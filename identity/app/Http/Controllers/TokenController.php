<?php

namespace App\Http\Controllers;

use App\Services\ApiResponseService;
use App\Services\TokenService;
use Illuminate\Http\JsonResponse;
use Illuminate\Http\Request;
use Illuminate\Support\Str;

class TokenController extends Controller
{
    public function generate($id_account): JsonResponse
    {

        $status = 200;
        $data = array();
        $errors = [];
        $message = "Generation d'un model de token pour un utilisateur";
        try {
            if (!is_numeric($id_account) || $id_account <= 0) {
                throw new \Exception("Id Account Invalid");
            }
            $token = TokenService::generate($id_account);
            $data['token_model'] = $token;
        } catch (\Exception $e) {
            $errors['exception'] = $e;
            $errors['details'] = [
                'id_account' => $id_account
            ];
            $status = 399;
            $message = $e->getMessage();
        }
        return ApiResponseService::apiResponse($status, $message, $data, $errors);
    }

    public function regenerate($id_account)
    {
        $status = 200;
        $token = NULL;
        $data = array();
        $errors = [];
        $message = "Regeneration du token pour l'id account " . $id_account;
        try {
            if (!is_numeric($id_account) || $id_account <= 0) {
                throw new \Exception("Id Account Invalid");
            }
            $token = TokenService::regenerateBarerToken($id_account, $token);
            $data['token'] = $token;
        } catch (\Exception $e) {
            $errors['exception'] = $e;
            $errors['details'] = [
                'id_account' => $id_account,
                'token' => $token
            ];
            $status = 399;
            $message = $e->getMessage();
        }
        return ApiResponseService::apiResponse($status, $message, $data, $errors);
    }

    public function validateMe(
        Request $request,
        array   $rules = [],
        array   $messages = [],
        array   $customAttributes = []
    )
    {
        $token = $request->header('Authorization');

        if (!$token || !Str::startsWith($token, 'Bearer ')) {
            return response()->json(['error' => 'Invalid token'], 401);
        }

        $token = Str::replaceFirst('Bearer ', '', $token);
        $usermy = \App\Models\Token::where('token', $token)->first();

        if (!$usermy) {
            return response()->json(['error' => 'Token not valid'], 401);
        }

        return response()->json([
            'valid' => true,
            'user' => $usermy
        ]);
    }
}
