<?php

use App\Http\Controllers\AccountController;
use Illuminate\Support\Facades\Route;
use App\Http\Controllers\Api\TokenController;

use App\Http\Controllers\PinController;
use App\Http\Controllers\PendingAuthController;

/**
 * @OA\Info(
 *     title="API Guess Who",
 *     version="1.0.0",
 *     description="Documentation pour l'API Guess Who.",
 *     @OA\Contact(
 *         email="support@votre-site.com"
 *     )
 * )
 */

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
Route::post('/login', [PinController::class, 'login']);
Route::post('/validate-pin', [PinController::class, 'validatePin']);

//Unlock
Route::post('/account/unlock', [AccountController::class, 'login']);
Route::post('/account/unlock/validate', [AccountController::class, 'validatePin']);

Route::get('/api/documentation', function () {
    return view('swagger.index');
});

// TOKEN ROUTER
Route::get('token', [TokenController::class, 'index']); // afficher un token généré
Route::get('token/gen/{id_account}', [TokenController::class, 'generate']); // générer un token pour un account
Route::get('token/regen/{id_account}', [TokenController::class, 'regenerate']); // régénérer un token pour un account
