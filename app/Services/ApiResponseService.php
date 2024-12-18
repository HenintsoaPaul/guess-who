<?php

namespace App\Services;

/**
 * Service pour gerer les fonctions de dates
 */
 class ApiResponseService {
    public static function apiResponse(int $code , string $message , array $data = [] , array $errors = [] ){
        $status = ApiResponseService::getResponseStatusByCode($code);
        $response =[
            'status'=>$status,
            'code'=>$code,
            'message'=>$message,
            'data'=>$data,
            'error'=>[
                'errors' => $errors,
                'details' => []
            ],
        ];
        return response()->json($response);
    }

    public static function getResponseStatusByCode($statusCode){
        switch ($statusCode) {
            case '200':
                return 'OK';
                break;
            case '400':
            case '404':
            case '500':
                return 'Error';
                break;
            default:
                return 'Undefined';
                break;
        }
    }
 }