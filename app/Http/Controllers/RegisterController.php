<?php

namespace App\Http\Controllers;

use App\Services\JsonResponseService;
use App\Services\RandomService;
use App\Mail\SendEmail;
use Illuminate\Support\Facades\Mail;
use App\Models\PendingRegister;
use Carbon\Carbon;
use Illuminate\Support\Facades\DB;
use Illuminate\Http\Request;

/**
 * @OA\Info(title="API Guess Who", version="1.0")
 * @OA\Server(url="http://localhost:8000")
 */
class RegisterController extends Controller
{
    protected $jsonResponse;
    protected $random;

    public function __construct(JsonResponseService $jsonResponse, RandomService $random)
    {
        $this->jsonResponse = $jsonResponse;
        $this->random = $random;
    }

    /**
     * @OA\Get(
     *     path="/register",
     *     summary="Valide les données d'entrée et retourne un code PIN",
     *     @OA\Response(
     *         response=200,
     *         description="Succès",
     *         @OA\JsonContent(
     *             @OA\Property(property="message", type="string", example="Vous avez recu votre code pin"),
     *             @OA\Property(property="pin", type="integer", example=1234)
     *         )
     *     ),
     *     @OA\Response(
     *         response=422,
     *         description="Données invalides",
     *         @OA\JsonContent(
     *             @OA\Property(property="error", type="string", example="Les données sont invalides."),
     *             @OA\Property(property="details", type="object")
     *         )
     *     )
     * )
     */
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

    /**
     * @OA\Post(
     *     path="/pendingregister",
     *     summary="Insère un enregistrement et envoie un code PIN par email",
     *     @OA\RequestBody(
     *         required=true,
     *         @OA\JsonContent(
     *             @OA\Property(property="email", type="string", example="example@example.com"),
     *             @OA\Property(property="password", type="string", example="securePassword")
     *         )
     *     ),
     *     @OA\Response(
     *         response=201,
     *         description="Enregistrement créé avec succès",
     *         @OA\JsonContent(
     *             @OA\Property(property="message", type="string", example="Pending register created successfully."),
     *             @OA\Property(property="data", type="object")
     *         )
     *     ),
     *     @OA\Response(
     *         response=422,
     *         description="Données invalides",
     *         @OA\JsonContent(
     *             @OA\Property(property="error", type="string", example="Les données sont invalides."),
     *             @OA\Property(property="details", type="object")
     *         )
     *     ),
     *     @OA\Response(
     *         response=500,
     *         description="Erreur serveur",
     *         @OA\JsonContent(
     *             @OA\Property(property="error", type="string", example="Une erreur est survenue lors de l'enregistrement."),
     *             @OA\Property(property="details", type="string")
     *         )
     *     )
     * )
     */
    public function insertRegister(Request $request)
    {
        try {
            $requestData = $request->validate([
                'email' => 'required|email',
                'password' => 'required|string|min:1',
            ]);
        } catch (\Illuminate\Validation\ValidationException $e) {
            return response()->json(['error' => 'Les données sont invalides.', 'details' => $e->errors()], 422);
        }

        $pin = $this->random->newPin();
        $validated = [
            'email' => $requestData['email'],
            'password' => $requestData['password'],
            'date_register' => Carbon::now(),
            'date_expiration' => Carbon::now()->addMinutes(10),
            'pin' => $pin,
        ];

        DB::beginTransaction();
        try {
            $pendingRegister = PendingRegister::create($validated);
            Mail::to($requestData['email'])->send(new SendEmail($pin));
            DB::commit();
        } catch (\Exception $e) {
            DB::rollback();
            return response()->json(['error' => 'Une erreur est survenue lors de l\'enregistrement.', 'details' => $e->getMessage()], 500);
        }

        return response()->json(['message' => 'Pending register created successfully.', 'data' => $pendingRegister], 201);
    }
}
