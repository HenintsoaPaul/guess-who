<?php

namespace App\Http\Controllers;

use App\Services\ApiResponseService;
use App\Services\TokenService;

class TokenController extends Controller
{
    public function index()
    {
        $data = array();
        $errors = array();
        $status = 200;
        $message = "Token generer";
        try {
            $token = TokenService::newToken();
            $data['token'] = $token ;
        }
        catch (\Exception $e) {
            $errors['exception'] = $e;
            $status = 400;
            $message = $e->getMessage();
        }
        return ApiResponseService::apiResponse($status,$message,$data,$errors);
    }

    public function generate($id_account): \Illuminate\Http\JsonResponse
    {

        $status = 200;
        $data = array();
        $errors = [];
        $message = "Generation d'un model de token pour un utilisateur";
        try {
            if(!is_numeric($id_account) || $id_account <= 0) {
                throw new \Exception("Id Account Invalid");
            }
            $token = TokenService::generate($id_account);
            $data['token_model'] = $token;
        }
        catch (\Exception $e) {
            $errors['exception'] = $e;
            $errors['details'] = [
                'id_account'=>$id_account
            ];
            $status = 399;
            $message = $e->getMessage();
        }
        return ApiResponseService::apiResponse($status,$message,$data,$errors);
    }

    public function regenerate($id_account)
    {

        $status = 200;
        $token = NULL;
        $data = array();
        $errors = [];
        $message = "Regeneration du token pour l'id account ".$id_account;
        try {
            if(!is_numeric($id_account) || $id_account <= 0) {
                throw new \Exception("Id Account Invalid");
            }
            $token = TokenService::regenerateBarerToken($id_account,$token);
            $data['token'] = $token;
        }
        catch (\Exception $e) {
            $errors['exception'] = $e;
            $errors['details'] = [
                'id_account'=>$id_account,
                'token'=>$token
            ];
            $status = 399;
            $message = $e->getMessage();
        }
        return ApiResponseService::apiResponse($status,$message,$data,$errors);
    }
}
