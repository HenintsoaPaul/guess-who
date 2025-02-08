<?php

namespace App\Http\Controllers;

use App\Mail\SendEmail;
use App\Models\Account;
use App\Models\PendingPwdChange;
use App\Services\JsonResponseService;
use App\Services\RandomService;
use App\Services\TokenService;
use Illuminate\Database\Eloquent\ModelNotFoundException;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Mail;
use Illuminate\Validation\ValidationException;


class AccountController extends Controller
{
    private JsonResponseService $jsonResponse;

    public function __construct(JsonResponseService $jsonResponse)
    {
        $this->jsonResponse = $jsonResponse;
    }

    /**
     * @OA\Post(
     *     path="/api/account/unlock",
     *     summary="Déverrouiller un compte utilisateur",
     *     description="Point d'accès permettant de déverrouiller un compte utilisateur existant en utilisant son adresse email.",
     *     tags={"compte", "gestion"},
     *     security={},
     *     @OA\RequestBody(
     *         required=true,
     *         @OA\JsonContent(
     *             type="object",
     *             required={"email"},
     *             @OA\Property(
     *                 property="email",
     *                 type="string",
     *                 format="email",
     *                 description="Adresse email du compte à déverrouiller",
     *                 example="utilisateur@exemple.com"
     *             )
     *         )
     *     ),
     *     @OA\Response(
     *         response=200,
     *         description="Compte déverrouillé avec succès",
     *         @OA\JsonContent(
     *             type="object",
     *             @OA\Property(
     *                 property="succes",
     *                 type="boolean",
     *                 example=true
     *             ),
     *             @OA\Property(
     *                 property="donnees",
     *                 type="object",
     *                 @OA\Property(
     *                     property="id_compte",
     *                     type="integer",
     *                     example=1
     *                 )
     *             ),
     *             @OA\Property(
     *                 property="message",
     *                 type="string",
     *                 example="Le compte a été déverrouillé avec succès."
     *             )
     *         )
     *     ),
     *     @OA\Response(
     *         response=400,
     *         description="Paramètres invalides",
     *         @OA\JsonContent(
     *             type="object",
     *             @OA\Property(
     *                 property="succes",
     *                 type="boolean",
     *                 example=false
     *             ),
     *             @OA\Property(
     *                 property="message",
     *                 type="string",
     *                 example="L'email est obligatoire et doit être valide."
     *             )
     *         )
     *     ),
     *     @OA\Response(
     *         response=404,
     *         description="Compte introuvable",
     *         @OA\JsonContent(
     *             type="object",
     *             @OA\Property(
     *                 property="succes",
     *                 type="boolean",
     *                 example=false
     *             ),
     *             @OA\Property(
     *                 property="message",
     *                 type="string",
     *                 example="Le compte avec cet email n'a pas été trouvé."
     *             )
     *         )
     *     ),
     *     @OA\Response(
     *         response=422,
     *         description="Erreur de validation",
     *         @OA\JsonContent(
     *             type="object",
     *             @OA\Property(
     *                 property="succes",
     *                 type="boolean",
     *                 example=false
     *             ),
     *             @OA\Property(
     *                 property="message",
     *                 type="string",
     *                 example="Format d'email invalide."
     *             )
     *         )
     *     ),
     *     @OA\Response(
     *         response=500,
     *         description="Erreur serveur interne",
     *         @OA\JsonContent(
     *             type="object",
     *             @OA\Property(
     *                 property="succes",
     *                 type="boolean",
     *                 example=false
     *             ),
     *             @OA\Property(
     *                 property="message",
     *                 type="string",
     *                 example="Une erreur technique est survenue."
     *             )
     *         )
     *     )
     * )
     */
    public function unlock(Request $request)
    {
        DB::beginTransaction();
        try {
            $email = $request->validate([
                'email' => 'required|email',
            ]);

            $account = Account::getByEmail($email);

            $account->unlockAccount();

            DB::commit();

            return $this->jsonResponse->success(
                'Account unlocked.',
                ['id_account' => $account->id_account]
            );
        } catch (ValidationException $e) {
            DB::rollBack();
            return $this->jsonResponse->validationError($e);
        } catch (ModelNotFoundException $e) {
            DB::rollBack();
            return $this->jsonResponse->error(
                'Email non trouvé',
                null,
                404
            );
        } catch (\Exception $e) {
            DB::rollBack();
            return $this->jsonResponse->error(
                'Erreur lors du déverrouillage du compte',
                null,
                500
            );
        }
    }

    /**
     * @throws \Exception
     */
    public function changePassword(Request $request): \Illuminate\Http\JsonResponse
    {
        try {
            $payload = $request->validate([
                'id_account' => 'required|numeric',
                'new_password' => 'required',
            ]);
            $token = TokenService::getBarerToken($request);
            if (!$token) {
                return $this->jsonResponse->tokenError();
            }

            DB::beginTransaction();
            try {
                $account = Account::getById($payload['id_account']);

                $mety = TokenService::isValidBarerToken($account->id_account, $token);
                if (!$mety) {
                    return $this->jsonResponse->tokenError();
                }

                // Send pin to email
                $pin = RandomService::newPin();
                Mail::to($account->email)->send(new SendEmail($pin));

                // Save in pending auth
                $delai = null;
                $pendingPwdChange = PendingPwdChange::addNew($pin, $account->id_account, $payload['new_password'], $delai);

                DB::commit();
                $data = [
                    'id' => $pendingPwdChange->id_pending_pwd_change,
                    'id_account' => $pendingPwdChange->id_account,
                    'pin' => $pendingPwdChange->pin
                ];
                return $this->jsonResponse->success('Password change email validation sent.', $data);
            } catch (\Exception $e) {
                return $this->jsonResponse->error('Invalid account.', $e->errors(), 422);
            }
        } catch (ValidationException $e) {
            return $this->jsonResponse->error('Invalid payload.', $e->errors(), 422);
        }
    }

    /**
     * @throws \Exception
     */
    public function validateChangePassword(Request $request): \Illuminate\Http\JsonResponse
    {
        try {
            $payload = $request->validate([
                'id' => 'required|numeric',
                'pin' => 'required',
            ]);
            $token = TokenService::getBarerToken($request);
            if (!$token) {
                return $this->jsonResponse->tokenError();
            }

            DB::beginTransaction();
            try {
                $pendingPwdChange = PendingPwdChange::getById($payload['id']);

                $account = Account::getById($pendingPwdChange->id_account);
                if (!TokenService::isValidBarerToken($account->id_account, $token)) {
                    return $this->jsonResponse->tokenError();
                }

                if ($payload['pin'] !== $pendingPwdChange->pin) {
                    return $this->jsonResponse->error('Invalid pin.', $payload, 422);
                }

                $pendingPwdChange->validate($account);
                DB::commit();

                $data = [
                    'id_account' => $pendingPwdChange->id_account,
                ];
                return $this->jsonResponse->success('Password change email validation sent.', $data);
            } catch (\Exception $e) {
                return $this->jsonResponse->error('Invalid account.', $e->errors(), 422);
            }
        } catch (ValidationException $e) {
            return $this->jsonResponse->error('Invalid payload.', $e->errors(), 422);
        }
    }
}
