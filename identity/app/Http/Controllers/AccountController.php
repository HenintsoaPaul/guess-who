<?php

namespace App\Http\Controllers;

use App\Mail\SendEmail;
use App\Models\Account;
use App\Models\PendingPwdChange;
use App\Services\JsonResponseService;
use App\Services\RandomService;
use App\Services\TokenService;
use Illuminate\Database\Eloquent\ModelNotFoundException;
use Illuminate\Http\JsonResponse;
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
     * @OA\Post(
     *     path="/api/account/change-password",
     *     summary="Changer le mot de passe d'un compte",
     *     description="Initie le processus de changement de mot de passe en envoyant un code PIN par email.",
     *     tags={"compte", "Authentification", "gestion", "password"},
     *     security={{"bearerAuth": {}}},
     *     @OA\RequestBody(
     *         required=true,
     *         @OA\JsonContent(
     *             type="object",
     *             required={"id_account", "new_password"},
     *             @OA\Property(
     *                 property="id_account",
     *                 type="integer",
     *                 description="Identifiant du compte",
     *                 example=1
     *             ),
     *             @OA\Property(
     *                 property="new_password",
     *                 type="string",
     *                 description="Nouveau mot de passe",
     *                 example="nouveau_mot_de_passe"
     *             )
     *         )
     *     ),
     *     @OA\Response(
     *         response=200,
     *         description="Email de validation envoyé avec succès",
     *         @OA\JsonContent(
     *             type="object",
     *             @OA\Property(
     *                 property="succès",
     *                 type="boolean",
     *                 example=true
     *             ),
     *             @OA\Property(
     *                 property="données",
     *                 type="object",
     *                 @OA\Property(
     *                     property="id",
     *                     type="integer",
     *                     example=1
     *                 ),
     *                 @OA\Property(
     *                     property="id_account",
     *                     type="integer",
     *                     example=1
     *                 ),
     *                 @OA\Property(
     *                     property="pin",
     *                     type="string",
     *                     example="1234"
     *                 )
     *             ),
     *             @OA\Property(
     *                 property="message",
     *                 type="string",
     *                 example="Email de validation envoyé avec succès"
     *             )
     *         )
     *     ),
     *     @OA\Response(
     *         response=401,
     *         description="Token invalide ou manquant",
     *         @OA\JsonContent(
     *             type="object",
     *             @OA\Property(
     *                 property="succès",
     *                 type="boolean",
     *                 example=false
     *             ),
     *             @OA\Property(
     *                 property="message",
     *                 type="string",
     *                 example="Token invalide ou manquant"
     *             )
     *         )
     *     ),
     *     @OA\Response(
     *         response=422,
     *         description="Données de validation invalides",
     *         @OA\JsonContent(
     *             type="object",
     *             @OA\Property(
     *                 property="succès",
     *                 type="boolean",
     *                 example=false
     *             ),
     *             @OA\Property(
     *                 property="message",
     *                 type="string",
     *                 example="Données de validation invalides"
     *             ),
     *         )
     *     ),
     *     @OA\Response(
     *         response=500,
     *         description="Erreur interne du serveur",
     *         @OA\JsonContent(
     *             type="object",
     *             @OA\Property(
     *                 property="succès",
     *                 type="boolean",
     *                 example=false
     *             ),
     *             @OA\Property(
     *                 property="message",
     *                 type="string",
     *                 example="Erreur interne du serveur"
     *             )
     *         )
     *     )
     * )
     */
    public function changePassword(Request $request): JsonResponse
    {
        try {
            $payload = $request->validate([
                'id_account' => 'required|numeric',
                'new_password' => 'required',
            ]);

            $token = TokenService::getBarerToken($request);
            if (!$token) {
                return $this->jsonResponse->tokenError(); // 401
            }

            $account = Account::getById($payload['id_account']);

            // Vérification de la validité du token
            $mety = TokenService::isValidBarerToken($account->id_account, $token);
            if (!$mety) {
                return $this->jsonResponse->tokenError(); // 401
            }

            $pin = RandomService::newPin();


            DB::beginTransaction();
            $pendingPwdChange = PendingPwdChange::addNew($pin, $account->id_account, $payload['new_password']);
            DB::commit();

            Mail::to($account->email)->send(new SendEmail($pin));

            $data = [
                'id' => $pendingPwdChange->id_pending_pwd_change,
                'id_account' => $pendingPwdChange->id_account,
                'pin' => $pendingPwdChange->pin
            ];

            return $this->jsonResponse->success(
                'Email de validation envoyé avec succès.',
                $data
            );
        } catch (ValidationException $e) {
            return $this->jsonResponse->error(
                'Données de validation invalides.',
                $e->errors(),
                422
            );
        } catch (ModelNotFoundException $e) {
            DB::rollBack();
            return $this->jsonResponse->error(
                'Compte non trouvé',
                null,
                404
            );
        } catch (\Exception $e) {
            DB::rollBack();
            return $this->jsonResponse->error(
                $e->getMessage(),
                null,
                500
            );
        }
    }

    /**
     * @OA\Post(
     *     path="/api/password/change/validation",
     *     summary="Valider changement de mot de passe",
     *     description="Point d'accès permettant de valider une demande de changement de mot de passe en utilisant un PIN temporaire.",
     *     tags={"compte", "Authentitfiaction", "gestion", "password"},
     *     security={{"bearerAuth": {}}},
     *     @OA\RequestBody(
     *         required=true,
     *         @OA\JsonContent(
     *             type="object",
     *             required={"id", "pin"},
     *             @OA\Property(
     *                 property="id",
     *                 type="integer",
     *                 description="Identifiant de la demande de changement de mot de passe",
     *                 example=123
     *             ),
     *             @OA\Property(
     *                 property="pin",
     *                 type="string",
     *                 description="Code PIN temporaire envoyé par email",
     *                 example="123456"
     *             )
     *         )
     *     ),
     *     @OA\Response(
     *         response=200,
     *         description="Validation réussie",
     *         @OA\JsonContent(
     *             type="object",
     *             @OA\Property(
     *                 property="succès",
     *                 type="boolean",
     *                 example=true
     *             ),
     *             @OA\Property(
     *                 property="données",
     *                 type="object",
     *                 @OA\Property(
     *                     property="id_account",
     *                     type="integer",
     *                     example=1
     *                 )
     *             ),
     *             @OA\Property(
     *                 property="message",
     *                 type="string",
     *                 example="Validation du changement de mot de passe envoyée avec succès."
     *             )
     *         )
     *     ),
     *     @OA\Response(
     *         response=400,
     *         description="Paramètres invalides",
     *         @OA\JsonContent(
     *             type="object",
     *             @OA\Property(
     *                 property="succès",
     *                 type="boolean",
     *                 example=false
     *             ),
     *             @OA\Property(
     *                 property="message",
     *                 type="string",
     *                 example="L'identifiant et le PIN sont requis."
     *             )
     *         )
     *     ),
     *     @OA\Response(
     *         response=401,
     *         description="Jetons non valides ou manquants",
     *         @OA\JsonContent(
     *             type="object",
     *             @OA\Property(
     *                 property="succès",
     *                 type="boolean",
     *                 example=false
     *             ),
     *             @OA\Property(
     *                 property="message",
     *                 type="string",
     *                 example="Jeton d'authentification invalide ou manquant."
     *             )
     *         )
     *     ),
     *     @OA\Response(
     *         response=403,
     *         description="Accès refusé",
     *         @OA\JsonContent(
     *             type="object",
     *             @OA\Property(
     *                 property="succès",
     *                 type="boolean",
     *                 example=false
     *             ),
     *             @OA\Property(
     *                 property="message",
     *                 type="string",
     *                 example="Impossible de valider le jeton pour ce compte."
     *             )
     *         )
     *     ),
     *     @OA\Response(
     *         response=422,
     *         description="PIN invalide",
     *         @OA\JsonContent(
     *             type="object",
     *             @OA\Property(
     *                 property="succès",
     *                 type="boolean",
     *                 example=false
     *             ),
     *             @OA\Property(
     *                 property="message",
     *                 type="string",
     *                 example="Le PIN fourni ne correspond pas à celui attendu."
     *             )
     *         )
     *     ),
     *     @OA\Response(
     *         response=500,
     *         description="Erreur lors du traitement de la demande.",
     *         @OA\JsonContent(
     *             type="object",
     *             @OA\Property(
     *                 property="succès",
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
    public function validateChangePassword(Request $request): JsonResponse
    {
        try {
            $payload = $request->validate([
                'id' => 'required|numeric',
                'pin' => 'required',
            ]);

            $token = TokenService::getBarerToken($request);
            if (!$token) {
                return $this->jsonResponse->tokenError(); // 401
            }

            $pendingPwdChange = PendingPwdChange::getById($payload['id']);

            $account = Account::getById($pendingPwdChange->id_account);

            if (!TokenService::isValidBarerToken($account->id_account, $token)) {
                return $this->jsonResponse->tokenError(); // 401
            }

            if ($payload['pin'] !== $pendingPwdChange->pin) {
                return $this->jsonResponse->error('Invalid pin.', $payload, 403);
            }

            DB::beginTransaction();
            $pendingPwdChange->validate($account);
            DB::commit();

            $data = [
                'id_account' => $pendingPwdChange->id_account,
            ];
            return $this->jsonResponse->success(
                'Password change email validation sent.',
                $data
            );
        } catch (ValidationException $e) {
            return $this->jsonResponse->error(
                'Données de validation invalides.',
                $e->errors(),
                422
            );
        } catch (ModelNotFoundException $e) {
            DB::rollBack();
            return $this->jsonResponse->error(
                'Compte non trouvé',
                null,
                404
            );
        } catch (\Exception $e) {
            DB::rollBack();
            return $this->jsonResponse->error(
                'Erreur lors du traitement de la demande.',
                null,
                500
            );
        }
    }
}
