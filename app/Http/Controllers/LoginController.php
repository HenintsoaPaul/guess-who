<?php

namespace App\Http\Controllers;

use App\Mail\SendEmail;
use App\Models\Account;
use App\Models\PendingAuth;
use App\Models\Token;
use App\Services\AuthService;
use App\Services\JsonResponseService;
use App\Services\RandomService;
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
            Mail::to($credentials['email'])->send(new SendEmail($pin));

            // insert pending_auth
            $delai = null;
            $pendingAuth = PendingAuth::addNew($pin, $account->id_account, $delai);

            DB::commit();

            $msg = "Pin sent to your email. Use the following 'id' to url '/login/validate'.";
            return $this->jsonResponse->success($msg, $pendingAuth->id_pending_auth);
        } catch (ValidationException $e) {
            DB::rollBack();
            return $this->jsonResponse->validationError($e);
        } catch (\Exception $exception) {
            DB::rollBack();
            $unauthorized = 401;
            return $this->jsonResponse->error($exception->getMessage(), $credentials, $unauthorized);
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
        $request->validate([
            'email' => 'required|email',
            'pin' => 'required|string',
        ]);

        $account = Account::where('email', $request->email)->first();
        if (!$account) {
            return response()->json(['message' => 'Utilisateur non trouvé'], 404);
        }

        $pendingAuth = PendingAuth::where('id_account', $account->id_account)
            ->where('date_expiration', '>', now())
            ->first();

        if (!$pendingAuth || $pendingAuth->pin !== $request->pin) {
            return response()->json(['message' => 'PIN invalide ou expiré'], 401);
        }

        $pendingAuth->delete();

        $account->resetAttempt();

        // todo: ovaina Alex
        $tokenString = Str::random(60);
        $token = Token::create([
            'token' => $tokenString,
            'id_account' => $account->id_account,
            'date_expiration' => now()->addDays(30),
        ]);
        // todo: ovaina Alex

        return response()->json([
            'message' => 'PIN validé. Token généré avec succès.',
            'token' => $tokenString,
            'expiration' => $token->date_expiration,
        ]);
    }
}
