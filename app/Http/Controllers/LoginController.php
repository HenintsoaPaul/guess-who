<?php

namespace App\Http\Controllers;

use App\Mail\SendEmail;
use App\Models\PendingAuth;
use App\Services\AuthService;
use App\Services\JsonResponseService;
use App\Services\RandomService;
use Illuminate\Http\JsonResponse;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Mail;
use Illuminate\Validation\ValidationException;

class LoginController extends Controller
{
    private JsonResponseService $jsonResponse;

    public function __construct(JsonResponseService $jsonResponse)
    {
        $this->jsonResponse = $jsonResponse;
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
    public function login(Request $request): JsonResponse
    {
        $credentials = null;
        DB::beginTransaction();
        try {
            $credentials = $request->validate([
                'email' => 'required|email',
                'password' => 'required',
            ]);

            $account = AuthService::authenticate($credentials['email'], $credentials['password']);

            // generate pin
            $pin = RandomService::newPin();

            // send pin on email
            Mail::to($credentials['email'])->send(new SendEmail($pin));

            // insert pending_auth
            $pendingAuth = PendingAuth::addNew($pin, $account->id_account);

            $res = $pendingAuth->save();
            if (!$res) {
                throw new \Exception("Failed to insert pending_auth.");
            }

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
}
