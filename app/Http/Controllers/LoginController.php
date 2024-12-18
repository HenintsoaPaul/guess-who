<?php

namespace App\Http\Controllers;

use App\Models\PendingAuth;
use App\Services\AuthService;
use App\Services\JsonResponseService;
use App\Services\RandomService;
use Illuminate\Http\JsonResponse;
use Illuminate\Http\Request;
use Illuminate\Validation\ValidationException;

class LoginController extends Controller
{
    private JsonResponseService $jsonResponse;

    public function __construct(JsonResponseService $jsonResponse)
    {
        $this->jsonResponse = $jsonResponse;
    }

    public function login(Request $request): JsonResponse
    {
        $credentials = null;
        try {
            $credentials = $request->validate([
                'email' => 'required|email',
                'password' => 'required',
            ]);

            DB::transaction(function () use ($credentials) {
                $account = AuthService::authenticate($credentials['email'], $credentials['password']);

                // generate pin
                $pin = RandomService::newPin();

                // send pin on email
                // TODO: atao eto...

                // insert pending_auth
                $pendingAuth = PendingAuth::addNew($pin, $account->id_account);

                $res = $pendingAuth->save();
                if (!$res) {
                    throw new \Exception("Failed to insert pending_auth.");
                }
                return $this->jsonResponse->success('Mety ilay controle', $pendingAuth );
            });
        } catch (ValidationException $e) {
            return $this->jsonResponse->validationError($e);

        } catch (\Exception $exception) {
            $unauthorized = 401;
            return $this->jsonResponse->error($exception->getMessage(), $credentials, $unauthorized);
        }
    }
}
