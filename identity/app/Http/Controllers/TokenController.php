<?php

namespace App\Http\Controllers;

use App\Services\ApiResponseService;
use App\Services\TokenService;
use App\Http\Controllers\ApiController;

class TokenController extends Controller
{
     /**
     * @OA\Get(
     *     path="/api/token",
     *     summary="Génère un nouveau token",
     *     description="Génère un nouveau token pour l'application.",
     *     tags={"token"},
     *     @OA\Response(
     *         response=200,
     *         description="Token généré avec succès.",
     *         @OA\JsonContent(
     *             @OA\Property(property="token", type="string", example="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
     *         )
     *     ),
     *     @OA\Response(
     *         response=400,
     *         description="Erreur serveur.",
     *         @OA\JsonContent(
     *             @OA\Property(property="error", type="string", example="Erreur interne.")
     *         )
     *     )
     * )
     */
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

    /**
     * @OA\Get(
     *     path="/api/token/generate/{id_account}",
     *     summary="Génère un modèle de token pour un utilisateur",
     *     description="Génère un modèle de token pour un utilisateur spécifique en fonction de l'ID de compte.",
     *     tags={"token"},
     *     @OA\Parameter(
     *         name="id_account",
     *         in="path",
     *         required=true,
     *         description="ID de l'utilisateur pour générer le modèle de token.",
     *         @OA\Schema(type="integer", example=123)
     *     ),
     *     @OA\Response(
     *         response=200,
     *         description="Modèle de token généré avec succès.",
     *         @OA\JsonContent(
     *             @OA\Property(property="token_model", type="string", example="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
     *         )
     *     ),
     *     @OA\Response(
     *         response=399,
     *         description="Erreur lors de la génération du token.",
     *         @OA\JsonContent(
     *             @OA\Property(property="error", type="string", example="ID de compte invalide.")
     *         )
     *     )
     * )
     */
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

        /**
     * @OA\Get(
     *     path="/api/token/regenerate/{id_account}",
     *     summary="Regénère un token pour un utilisateur",
     *     description="Regénère un token pour un utilisateur spécifique en fonction de l'ID de compte.",
     *     tags={"token"},
     *     @OA\Parameter(
     *         name="id_account",
     *         in="path",
     *         required=true,
     *         description="ID de l'utilisateur pour régénérer le token.",
     *         @OA\Schema(type="integer", example=123)
     *     ),
     *     @OA\Response(
     *         response=200,
     *         description="Token régénéré avec succès.",
     *         @OA\JsonContent(
     *             @OA\Property(property="token", type="string", example="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
     *         )
     *     ),
     *     @OA\Response(
     *         response=399,
     *         description="Erreur lors de la régénération du token.",
     *         @OA\JsonContent(
     *             @OA\Property(property="error", type="string", example="ID de compte invalide.")
     *         )
     *     )
     * )
     */
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
