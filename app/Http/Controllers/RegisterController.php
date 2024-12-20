<?php 

namespace App\Http\Controllers;

use Illuminate\Http\Request; 
use App\Services\JsonResponseService;
use App\Services\RandomService;

class RegisterController extends Controller
{
    protected $jsonResponse;
    protected $random;

    public function __construct(JsonResponseService $jsonResponse, RandomService $random)
    {
        $this->jsonResponse = $jsonResponse;
        $this->random = $random;
    }
    
    public function controlInput(Request $request)
    {
        try {
            $information = $request->validate([
                'email' => 'required|email',
                'password' => 'required|string|min:1',
            ]);
        } catch (\Illuminate\Validation\ValidationException $e) {
            return $this->jsonResponse->error('Les données sont invalides.', $e->errors(), 422);
        }

        return $this->jsonResponse->success('Vous avez recu votre code pin', $this->random->newPin());        
    }

    public function controlInputValidationRegister(Request $request)
    {
        try {
            // Validation des entrées
            $data = $request->validate([
                'id_pending_register' => 'required|integer',
                'pin' => 'required|string', // Le pin ne peut être null ou une chaîne
            ]);

            // Vérification et traitement des entrées
            $idRegister = $data['idRegister'];
            $pin = e($data['pin']); // Échappe le pin ou le rend null

            if (is_null($idRegister)) {
                return $this->jsonResponse->error('L\'identifiant idRegister est requis.', [], 422);
        }

        // Effectuer d'autres traitements nécessaires pour la validation
        // Exemple : Rechercher une correspondance dans la base de données
        // $register = Register::find($idRegister);
        // if (!$register || $register->pin !== $pin) {
        //     return $this->jsonResponse->error('Les informations de validation sont incorrectes.', [], 422);
        // }

            return $this->jsonResponse->success('Validation réussie.', [
                'idRegister' => $idRegister,
                'pin' => $pin,
        ]);
    } catch (\Illuminate\Validation\ValidationException $e) {
        return $this->jsonResponse->error('Les données sont invalides.', $e->errors(), 422);
    }
}


}