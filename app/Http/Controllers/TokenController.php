<?php
namespace App\Http\Controllers;

use App\Models\account;  
use App\Models\PendingAuth;
use App\Models\Token;
use Illuminate\Support\Str;
use Illuminate\Http\Request;
use App\Services\RandomService;
use App\Mail\SendEmail;
use Illuminate\Support\Facades\Mail;

class TokenController extends Controller
{
    private $randomService;

    /**
     * Injecte le RandomService dans le contrôleur.
     *
     * @param RandomService $randomService
     */
    public function __construct(randomService $randomService)
    {
        $this->randomService = $randomService;
    }

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


    // public function sendPin(Request $request)
    // {
    //     $request->validate(['email' => 'required|email']);

    //     $account = Account::where('email', $request->email)->first();
    //     if (!$account) {
    //         return response()->json(['message' => 'Utilisateur non trouvé'], 404);
    //     }

    //     $pin = $this->pinService->generatePin();
    //     $pendingAuth = PendingAuth::create([
    //         'pin' => bcrypt($pin), 
    //         'id_account' => $account->id_account,
    //         'date_creation' => now(),
    //         'date_expiration' => now()->addMinutes(10), 
    //     ]);

    //     Mail::to($request->email)->send(new SendPinEmail($pin));

    //     return response()->json(['message' => 'Code PIN envoyé par email']);
    // }

    // public function generateToken(Request $request)
    // {
    //     $request->validate(['email' => 'required|email']);

    //     $account = Account::where('email', $request->email)->first();
    //     if (!$account) {
    //         return response()->json(['message' => 'Utilisateur non trouvé'], 404);
    //     }

    //     $tokenString = Str::random(60);
    //     $token = Token::create([
    //         'token' => $tokenString,
    //         'id_account' => $account->id_account,
    //         'date_expiration' => now()->addDays(30),
    //     ]);

    //     return response()->json([
    //         'message' => 'Token généré avec succès',
    //         'token' => $tokenString,
    //         'expiration' => $token->date_expiration,
    //     ]);
    // }
    
    
}
