<?php

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;
use App\Http\Controllers\Api\TokenController;

use App\Http\Controllers\PinController;
use App\Http\Controllers\PendingAuthController;


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

Route::resource('emails', PinController::class);

Route::post('emails/sendPin', [PinController::class, 'sendPinCode']);
Route::get('/pending-auth/{id}', [PendingAuthController::class, 'getPendingAuthById']);

Route::post('/login', [\App\Http\Controllers\LoginController::class, 'login']);

Route::middleware('auth:sanctum')->get('/user', function (Request $request) {
    return $request->user(); 
});

// TOKEN ROUTER
Route::get('token',[TokenController::class,'index']); // afficher un token generer
Route::get('token/gen/{id_account}',[TokenController::class,'generate']); // generer un token pour un account
Route::get('token/regen/{id_account}',[TokenController::class,'regenerate']); // regenerer un token pour un account