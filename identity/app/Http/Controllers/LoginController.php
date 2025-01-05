<?php

namespace App\Http\Controllers;

/**
 * @OA\Info(
 *     title="API Guess Who",
 *     version="1.0.0",
 *     description="Documentation pour l'API Guess Who.",
 *     @OA\Contact(
 *         email="support@votre-site.com"
 *     )
 * )
 */

use App\Mail\SendEmail;
use App\Models\Account;
use App\Models\PendingAuth;
use App\Services\AuthService;
use App\Services\JsonResponseService;
use App\Services\RandomService;
use App\Services\TokenService;
use Illuminate\Http\JsonResponse;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Mail;
use Illuminate\Support\Str;
use Illuminate\Validation\ValidationException;

class LoginController extends Controller
{
    private JsonResponseService  $jsonResponse;

    public function __construct(JsonResponseService $responseService)
    {
        $this->jsonResponse = $responseService;
    }

    /**
     * @OA\Post(
     *     path="/api/login",
     *     summary="Login user",
     *     @OA\RequestBody(
     *         required=true,
     *         @OA\JsonContent(
     *             type="object",
     *             @OA\Property(property="email", type="string", format="email"),
     *             @OA\Property(property="password", type="string")
     *         )
     *     ),
     *     @OA\Response(
     *         response=200,
     *         description="Successful login"
     *     ),
     *     @OA\Response(
     *         response=404,
     *         description="Email non trouvé"
     *     ),
     *     @OA\Response(
     *         response=401,
     *         description="Mot de passe incorrect"
     *     ),
     *     @OA\Response(
     *         response=403,
     *         description="Compte inactif"
     *     )
     * )
     */
    public function login(Request $request)
    {
        DB::beginTransaction();
        try {
            $credentials = $request->validate([
                'email' => 'required|email',
                'password' => 'required',
            ]);

            $account = AuthService::authenticate($credentials['email'], $credentials['password']);

            // send pin to email
            $pin = RandomService::newPin();
            // TODO: uncomment this
//            Mail::to($credentials['email'])->send(new SendEmail($pin));
            // TODO: uncomment this

            // insert pending_auth
            $delai = null;
            $pendingAuth = PendingAuth::addNew($pin, $account->id_account, $delai);

            DB::commit();

            $msg = "Pin sent to your email. Use the following 'id' to url '/login/validate'.";
            $data = [
                'account' => $account->email,
                'pin' =>  $pin,
            ];
//            return $this->jsonResponse->success($msg, $account->email);
            return $this->jsonResponse->success($msg, $data);
        } catch (ValidationException $e) {
            DB::rollBack();
            return $this->jsonResponse->validationError($e);
        } catch (\Exception $exception) {
            DB::rollBack();
            $unauthorized = 401;
            return $this->jsonResponse->error($exception->getMessage(), null, $unauthorized);
        }
    }

    /**
     * @OA\Post(
     *     path="/api/validate-pin",
     *     summary="Validation du PIN",
     *     description="Valide le PIN envoyé par email et génère un token d'accès.",
     *     operationId="validatePin",
     *     tags={"Authentification"},
     *     @OA\RequestBody(
     *         required=true,
     *         @OA\JsonContent(
     *             required={"email", "pin"},
     *             @OA\Property(property="email", type="string", format="email", example="user@example.com"),
     *             @OA\Property(property="pin", type="string", example="123456")
     *         )
     *     ),
     *     @OA\Response(
     *         response=200,
     *         description="PIN validé et token généré.",
     *         @OA\JsonContent(
     *             @OA\Property(property="message", type="string", example="PIN validé. Token généré avec succès."),
     *             @OA\Property(property="token", type="string", example="abc123def456ghi789"),
     *             @OA\Property(property="expiration", type="string", format="date-time", example="2024-12-31T23:59:59")
     *         )
     *     ),
     *     @OA\Response(
     *         response=404,
     *         description="Utilisateur non trouvé.",
     *         @OA\JsonContent(
     *             @OA\Property(property="message", type="string", example="Utilisateur non trouvé.")
     *         )
     *     ),
     *     @OA\Response(
     *         response=401,
     *         description="PIN invalide ou expiré.",
     *         @OA\JsonContent(
     *             @OA\Property(property="message", type="string", example="PIN invalide ou expiré.")
     *         )
     *     )
     * )
     */
    public function validatePin(Request $request): JsonResponse
    {
        DB::beginTransaction();
        try {
            $request->validate([
                'email' => 'required|email',
                'pin' => 'required|string',
            ]);

            $account = Account::where('email', $request->email)->first();
            if (!$account) {
                throw new \Exception("Utilisateur non trouvé");
            }

            $pendingAuth = PendingAuth::where('id_account', $account->id_account)
                ->where('date_expiration', '>', now())
                ->first();
            if (!$pendingAuth || $pendingAuth->pin !== $request->pin) {
                throw new \Exception("PIN invalide ou expiré");
            }
            $pendingAuth->delete();

            $account->resetAttempt();

            $tokenModel = TokenService::generate($account->id_account);

            $msg = 'PIN validé. Token généré avec succès.';
            $data = [
                'token' => $tokenModel->token,
                'expiration' => $tokenModel->date_expiration,
            ];
            return $this->jsonResponse->success($msg, $data);
        } catch (ValidationException $e) {
            DB::rollBack();
            return $this->jsonResponse->validationError($e);
        } catch (\Exception $exception) {
            DB::rollBack();
            return $this->jsonResponse->error($exception->getMessage(), null, 401);
        }
    }
}
