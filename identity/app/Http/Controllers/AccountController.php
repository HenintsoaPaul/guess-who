<?php

namespace App\Http\Controllers;

use App\Http\Controllers\ApiController;
use App\Mail\SendEmail;
use App\Models\Account;
use App\Models\PendingPwdChange;
use App\Services\JsonResponseService;
use App\Services\RandomService;
use App\Services\TokenService;
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
     *     summary="Débloque un compte utilisateur",
     *     description="Permet de débloquer un compte utilisateur en utilisant l'email.",
     *     tags={"Account"},
     *     @OA\RequestBody(
     *         required=true,
     *         @OA\JsonContent(
     *             @OA\Property(property="email", type="string", example="user@example.com")
     *         )
     *     ),
     *     @OA\Response(
     *         response=200,
     *         description="Compte débloqué avec succès.",
     *         @OA\JsonContent(
     *             @OA\Property(property="id_account", type="integer", example=123)
     *         )
     *     ),
     *     @OA\Response(
     *         response=422,
     *         description="Email invalide.",
     *         @OA\JsonContent(
     *             @OA\Property(property="error", type="string", example="Invalid email.")
     *         )
     *     ),
     *     @OA\Response(
     *         response=500,
     *         description="Erreur serveur.",
     *         @OA\JsonContent(
     *             @OA\Property(property="error", type="string", example="An error occurred.")
     *         )
     *     )
     * )
     * @throws \Exception
     */
    public function unlock(Request $request)
    {
        try {
            $email = $request->validate([
                'email' => 'required|email',
            ]);

            DB::beginTransaction();
            try {
                $account = Account::getByEmail($email);
                $account->unlockAccount();
                DB::commit();

                $data = [
                    'id_account' => $account->id_account
                ];
                return $this->jsonResponse->success('Account unlocked.', $data);
            } catch (\Exception $e) {
                DB::rollBack();
                throw $e;
            }
        } catch (ValidationException $e) {
            return $this->jsonResponse->error('Invalid email.', $e->errors(), 422);
        }
    }

     /**
     * @OA\Post(
     *     path="/api/account/password",
     *     summary="Demande de changement de mot de passe",
     *     description="Permet de lancer la procédure de changement de mot de passe en envoyant un code PIN à l'email.",
     *     tags={"Account"},
     *     @OA\RequestBody(
     *         required=true,
     *         @OA\JsonContent(
     *             @OA\Property(property="id_account", type="integer", example=123),
     *             @OA\Property(property="new_password", type="string", example="newPassword123")
     *         )
     *     ),
     *     @OA\Response(
     *         response=200,
     *         description="Email de validation envoyé pour le changement de mot de passe.",
     *         @OA\JsonContent(
     *             @OA\Property(property="id", type="integer", example=1),
     *             @OA\Property(property="id_account", type="integer", example=123),
     *             @OA\Property(property="pin", type="string", example="123456")
     *         )
     *     ),
     *     @OA\Response(
     *         response=422,
     *         description="Données invalides.",
     *         @OA\JsonContent(
     *             @OA\Property(property="error", type="string", example="Invalid payload.")
     *         )
     *     ),
     *     @OA\Response(
     *         response=500,
     *         description="Erreur serveur.",
     *         @OA\JsonContent(
     *             @OA\Property(property="error", type="string", example="An error occurred.")
     *         )
     *     )
     * )
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
     * @OA\Post(
     *     path="/api/account/password/validate",
     *     summary="Valide un changement de mot de passe en utilisant un code PIN",
     *     description="Valide la demande de changement de mot de passe avec un PIN envoyé par email.",
     *     tags={"Account"},
     *     @OA\RequestBody(
     *         required=true,
     *         @OA\JsonContent(
     *             @OA\Property(property="id", type="integer", example=1),
     *             @OA\Property(property="pin", type="string", example="123456")
     *         )
     *     ),
     *     @OA\Response(
     *         response=200,
     *         description="Changement de mot de passe validé avec succès.",
     *         @OA\JsonContent(
     *             @OA\Property(property="id_account", type="integer", example=123)
     *         )
     *     ),
     *     @OA\Response(
     *         response=422,
     *         description="PIN invalide ou erreur lors de la validation.",
     *         @OA\JsonContent(
     *             @OA\Property(property="error", type="string", example="Invalid pin.")
     *         )
     *     ),
     *     @OA\Response(
     *         response=500,
     *         description="Erreur serveur.",
     *         @OA\JsonContent(
     *             @OA\Property(property="error", type="string", example="An error occurred.")
     *         )
     *     )
     * )
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
