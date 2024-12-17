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
}
