<?php

namespace App\Services;

/**
 * Service pour gerer les fonctions de dates
 */
 class ApiResponseService {
    // public static function apiResponse(int $code , string $message , array $data = [] , array $errors = [] ){
    //     $status = ApiResponseService::getResponseStatusByCode($code);
    //     $response =[
    //         'succes'=>$status,
    //         'status'=>$code,
    //         'message'=>$message,
    //         'data'=>$data,
    //         'error'=>$errors,
    //     ];
    //     return response()->json($response);
    // }

    public static function apiResponse(int $code , string $message , array $data = [] , array $errors = [] ){
        $jsonResonse = new JsonResponseService();
        if (empty($data) && !empty($errors)){
            return $jsonResonse->error($message,$errors,$code);
        }
        return $jsonResonse->success($message,$data,$code);
    }

    public static function getResponseStatusByCode($statusCode){
        switch ($statusCode) {
            case '200':
                return 'Ttrue';
                break;
            case '400':
            case '404':
            case '500':
                return 'False';
                break;
            default:
                return 'False';
                break;
        }
    }
 }