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

use App\Models\Account;
use App\Models\PendingAuth;
use App\Models\Token;
use Illuminate\Support\Str;
use Illuminate\Http\Request;
use App\Services\RandomService;
use App\Mail\SendEmail;
use Illuminate\Support\Facades\Mail;
use OpenApi\Annotations as OA;

class PinController extends Controller
{
    private $randomService;

    /**
     * Injecte le RandomService dans le contrôleur.
     *
     * @param RandomService $randomService
     */
    public function __construct(RandomService $randomService)
    {
        $this->randomService = $randomService;
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
        $request->validate([
            'email' => 'required|email',
            'password' => 'required',
        ]);

        $email = $request->input('email');
        $password = $request->input('password');

        $account = Account::authenticate($email, $password);

        if ($account === null) {
            return response()->json(['message' => 'Email non trouvé'], 404);
        }

        if ($account === false) {
            return response()->json(['message' => 'Mot de passe incorrect'], 401);
        }

        if ($account->typeAccountState && $account->typeAccountState->val !== 'Active') {
            return response()->json(['message' => 'Compte inactif'], 403);
        }

        $pin = $this->randomService->newPin();
        PendingAuth::create([
            'pin' => $pin,
            'id_account' => $account->id_account,
            'date_creation' => now()->addHours(3),
            'date_expiration' => now()->addHours(3)->addMinutes(30),
        ]);

        Mail::to($email)->send(new SendEmail($pin));

        return response()->json([
            'message' => 'Login validé. Un code PIN a été envoyé à votre email.',
        ]);
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
    public function validatePin(Request $request)
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

        $tokenString = Str::random(60);
        $token = Token::create([
            'token' => $tokenString,
            'id_account' => $account->id_account,
            'date_expiration' => now()->addDays(30),
        ]);

        return response()->json([
            'message' => 'PIN validé. Token généré avec succès.',
            'token' => $tokenString,
            'expiration' => $token->date_expiration,
        ]);
    }
}