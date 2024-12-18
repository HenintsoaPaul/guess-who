<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use Illuminate\Http\Request;
use App\Services\ApiResponseService;
use App\Services\TokenService;

class TokenController extends Controller
{
    public function index()
    {
        $token = TokenService::newToken();
        $data = ['token',$token];
        $errors = [];
        return ApiResponseService::apiResponse('200',"Token generer",$data,$errors);
    }
    public function generate()
    {
        $token = TokenService::generate(1);
        $data = ['token',$token];
        $errors = [];
        return ApiResponseService::apiResponse('200',"Gen Gen",$data,$errors);
    }
}
