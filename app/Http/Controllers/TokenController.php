<?php
namespace App\Http\Controllers;

use App\Models\account;  
use App\Models\Token;
use Illuminate\Support\Str;
use Illuminate\Http\Request;

class TokenController extends Controller
{
    /**
     * Génére un token pour un utilisateur.
     *
     * @param  int  $userId
     * @return \Illuminate\Http\Response
     */
    public function generateToken($userId)
    {
        $account = Account::find($userId);

        if (!$account) {
            return response()->json(['message' => 'Utilisateur non trouvé'], 404);
        }

        $tokenString = Str::random(60);

        $token = Token::create([
            'token' => $tokenString,
            'id_account' => $account->id_account,  
            'date_expiration' => now()->addDays(30),  
        ]);

        return response()->json([
            'message' => 'Token généré avec succès',
            'token' => $tokenString,
            'expiration' => $token->date_expiration
        ]);
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

        if ($account->typeAccountState && $account->typeAccountState->name !== 'active') {
            return response()->json(['message' => 'Compte inactif'], 403);
        }

        $token = base64_encode(bin2hex(random_bytes(30)));

        return response()->json([
            'message' => 'Connexion réussie',
            'token' => $token,
            'account' => $account,
        ]);
    }
}
