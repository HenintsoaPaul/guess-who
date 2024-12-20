<?php

use App\Http\Controllers\AccountController;
use App\Http\Controllers\LoginController;
use App\Http\Controllers\RegisterController;
use App\Http\Controllers\TokenController;
use Illuminate\Support\Facades\Route;


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

// Route::resource('emails', PinController::class);

// Route::post('emails/sendPin', [PinController::class, 'sendPinCode']);

// Route::post('/generate-token/{userId}', [TokenController::class, 'generateToken']);
// Route::get('/pending-auth/{id}', [PendingAuthController::class, 'getPendingAuthById']);
// Route::post('/login', [TokenController::class, 'login']);

//Login
Route::post('/login', [LoginController::class, 'login']);
Route::post('/validate-pin', [LoginController::class, 'validatePin']);

//Unlock
Route::post('/account/unlock', [AccountController::class, 'unlock']);
Route::post('/account/change/password', [AccountController::class, 'changePassword']);
Route::post('/account/change/password/validate', [AccountController::class, 'validateChangePassword']);

Route::get('/api/documentation', function () {
    return view('swagger.index');
});


// Register
Route::get('/register', [RegisterController::class, 'controlInput']);


// TOKEN ROUTER
Route::get('token', [TokenController::class, 'index']); // afficher un token généré
Route::get('token/gen/{id_account}', [TokenController::class, 'generate']); // générer un token pour un account
Route::get('token/regen/{id_account}', [TokenController::class, 'regenerate']); // régénérer un token pour un account
