<?php

namespace App\Http\Controllers;

use App\Services\JsonResponseService;
use App\Services\RandomService;
use App\Services\TimesService;
use App\Services\TokenService;
use App\Mail\SendEmail;
use Illuminate\Support\Facades\Hash;
use Illuminate\Support\Facades\Mail;
use App\Models\PendingRegister;
use App\Models\Account;
use Carbon\Carbon;
use Illuminate\Support\Facades\DB;
use App\Services\PendingRegisterService;
use Illuminate\Http\Request;
use Illuminate\Validation\ValidationException;

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
     * @OA\Post(
     *     path="/api/register",
     *     summary="Enregistrement utilisateur",
     *     description="Insère un enregistrement et envoie un code PIN par email",
     *     tags={"Enregistrement"},
     *     @OA\RequestBody(
     *         required=true,
     *         @OA\JsonContent(
     *             @OA\Property(property="email", type="string", example="example@example.com"),
     *             @OA\Property(property="password", type="string", example="securePassword")
     *         )
     *     ),
     *     @OA\Response(
     *         response=200,
     *         description="Enregistrement créé avec succès",
     *         @OA\JsonContent(
     *             @OA\Property(property="message", type="string", example="Pending register created successfully."),
     *             @OA\Property(property="data", type="object")
     *         )
     *     ),
     *     @OA\Response(
     *         response=399,
     *         description="Données invalides",
     *         @OA\JsonContent(
     *             @OA\Property(property="error", type="string", example="Les données sont invalides."),
     *             @OA\Property(property="details", type="object")
     *         )
     *     ),
     *     @OA\Response(
     *         response=398,
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

            // Verifier hoe mbola tsy misy mampiasa ilay email
            if (Account::where('email', $requestData['email'])->first() != null) {
                return $this->jsonResponse->error(
                    "Email deja utilise",
                    null,
                    401
                );
            }

            // Verifier hoe efa nandefasana PIN ilay olona
            DB::beginTransaction();
            $pendingRegister = PendingRegister::where('email', $requestData['email'])->first();
            if ($pendingRegister != null) {
                if ($pendingRegister->isExpired()) {
                    $pendingRegister->delete();
                } else {
                    return $this->jsonResponse->error(
                        "PIN deja envoye vers votre email.",
                        null,
                        401
                    );
                }
            }

            $validated = [
                'email' => $requestData['email'],
                'password' => Hash::make($requestData['password']),
                'date_register' => Carbon::now(),
                'date_expiration' => TimesService::genExpirationDateForRegister(),
                'pin' => $this->random->newPin(),
            ];
            $pendingRegister = PendingRegister::create($validated);
            DB::commit();

            // TODO: Alefa email hoe firy ny delai de validation nle pin
            Mail::to($requestData['email'])->send(new SendEmail($pendingRegister->pin));

            $data = [
                'email' => $pendingRegister->email,
                'pin' => $pendingRegister->pin,
                'date_expiration' => $pendingRegister->date_expiration
            ];

            return $this->jsonResponse->success(
                "Pending register created successfully.",
                $data
            );
        } catch (ValidationException $e) {
            return $this->jsonResponse->error(
                'Les données sont invalides.',
                $e->errors(),
                422
            );
        } catch (\Exception $e) {
            DB::rollback();
            return $this->jsonResponse->error(
                $e->getMessage(),
                [],
                500
            );
        }
    }

    /**
     * @OA\Post(
     *     path="/api/register/validate",
     *     summary="Validation du PIN",
     *     description="Valide un enregistrement en utilisant un code PIN obtenu par email",
     *     operationId="validateRegisterPin",
     *     tags={"Enregistrement"},
     *     @OA\RequestBody(
     *         required=true,
     *         @OA\JsonContent(
     *             @OA\Property(property="email", type="string", example="example@example.com"),
     *             @OA\Property(property="pin", type="string", example="123456")
     *         )
     *     ),
     *     @OA\Response(
     *         response=200,
     *         description="Inscription valider",
     *         @OA\JsonContent(
     *             @OA\Property(property="message", type="string", example="Pending register created successfully."),
     *             @OA\Property(property="data", type="object")
     *         )
     *     ),
     *     @OA\Response(
     *         response=399,
     *         description="Données invalides",
     *         @OA\JsonContent(
     *             @OA\Property(property="error", type="string", example="Les données sont invalides."),
     *             @OA\Property(property="details", type="object")
     *         )
     *     ),
     *     @OA\Response(
     *         response=398,
     *         description="Erreur lors de la Validation d'Enregistrement",
     *         @OA\JsonContent(
     *             @OA\Property(property="error", type="string", example="Une erreur est survenue lors de l'enregistrement."),
     *             @OA\Property(property="details", type="string")
     *         )
     *     )
     * )
     */
    public function validateRegister(Request $request)
    {
        try {
            // Validation des entrées
            $payload = $request->validate([
                'email' => 'required|email',
                'pin' => 'required|string', // Le pin ne peut être null ou une chaîne
            ]);
        } catch (\Illuminate\Validation\ValidationException $e) {
            return $this->jsonResponse->error('Les données sont invalides.', [], 399);
        }

        DB::beginTransaction();
        try {
            // Vérification et traitement des entrées
            $email = $payload['email'];
            $pin = e($payload['pin']); // Échappe le pin ou le rend null

            $account = PendingRegisterService::validateAccountRegister($email, $pin);

            // Generate token
            $tokenModel = TokenService::generate($account->id_account);

            DB::commit();
            $data = [
                'token' => $tokenModel->token,
                'expiration' => $tokenModel->date_expiration,
                'hashed_password' => $account->password
            ];
            return $this->jsonResponse->success("Inscription valider", $data);

        } catch (\Exception $err) {
            DB::rollBack();
            return $this->jsonResponse->error(
                $err->getMessage(),
                null,
                500
            );
        }
    }
}
