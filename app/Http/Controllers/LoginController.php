<?php

namespace App\Http\Controllers;

use App\Models\Account;
use App\Services\JsonResponseService;
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

            $account = Account::authenticate($credentials['email'], $credentials['password']);
            return $this->jsonResponse->success('Mety ilay controle', $account );

        } catch (ValidationException $e) {
            return $this->jsonResponse->validationError($e);

        } catch (\Exception $exception) {
            $unauthorized = 401;
            return $this->jsonResponse->error($exception->getMessage(), $credentials, $unauthorized);
        }
    }
}
