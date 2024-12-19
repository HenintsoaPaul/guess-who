<?php 

namespace App\Http\Controllers;

use Illuminate\Http\Request; 
use App\Services\JsonResponseService;
use App\Services\RandomService;
use App\Mail\SendEmail;
use Illuminate\Support\Facades\Mail;
use App\Models\PendingRegister;
use Carbon\Carbon;
use Illuminate\Support\Facades\DB;


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

    public function insertRegister(Request $request)
    {
        // Valider les données d'entrée
        try {
            $requestData = $request->validate([
                'email' => 'required|email',
                'password' => 'required|string|min:1',
            ]);
        } catch (\Illuminate\Validation\ValidationException $e) {
            return response()->json(['error' => 'Les données sont invalides.', 'details' => $e->errors()], 422);
        }
    
        // Générer le PIN
        $pin = $this->random->newPin();
    
        // Initialiser les valeurs
        $validated = [
            'email' => $requestData['email'],
            'password' => $requestData['password'],
            'date_register' => Carbon::now(),
            'date_expiration' => Carbon::now()->addMinutes(10),
            'pin' => $pin,
        ];
    
        DB::beginTransaction();
        try {
            // Créer un nouvel enregistrement
            $pendingRegister = PendingRegister::create($validated);
        
            // Envoyer l'email
            Mail::to($requestData['email'])->send(new SendEmail($pin));
        
            // Confirmer la transaction
            DB::commit();
        } catch (\Exception $e) {
            // Annuler la transaction en cas d'erreur
            DB::rollback();
            return response()->json(['error' => 'Une erreur est survenue lors de l\'enregistrement.', 'details' => $e->getMessage()], 500);
        }
    
        // Retourner une réponse JSON
        return response()->json(['message' => 'Pending register created successfully.', 'data' => $pendingRegister], 201);
    }

}