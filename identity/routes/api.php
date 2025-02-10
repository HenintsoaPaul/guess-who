<?php

use App\Http\Controllers\AccountController;
use App\Http\Controllers\RegisterController;
use Illuminate\Support\Facades\Route;
use App\Http\Controllers\LoginController;
use App\Http\Controllers\PendingAuthController;
use App\Http\Controllers\TokenController;


/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| is assigned the "api" middleware group. Enjoy building your API!
|
*/

// Docs
Route::get('/api/documentation', function () {
    return view('swagger.index');
});

// Login
Route::post('/login', [LoginController::class, 'login']);
Route::post('/login/validate', [LoginController::class, 'validatePin']);

// Account (unlock)
Route::post('/account/unlock', [AccountController::class, 'unlock']);

// Account (change password)
Route::post('/account/password', [AccountController::class, 'changePassword']);
Route::post('/account/password/validate', [AccountController::class, 'validateChangePassword']);

// Register
Route::post('/register', [RegisterController::class, 'insertRegister']);
Route::post('/register/validate', [RegisterController::class, 'validateRegister']);

// Token
Route::get('/token/gen/{id_account}', [TokenController::class, 'generate']);
Route::get('/token/regen/{id_account}', [TokenController::class, 'regenerate']);
Route::get('/token/validate', [TokenController::class, 'validateMe']);
