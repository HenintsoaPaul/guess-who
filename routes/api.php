<?php

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;

use App\Http\Controllers\PinController;

use App\Http\Controllers\TokenController;
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

Route::post('/generate-token/{userId}', [TokenController::class, 'generateToken']);
Route::get('/pending-auth/{id}', [PendingAuthController::class, 'getPendingAuthById']);
Route::post('/login', [TokenController::class, 'login']);
n

Route::middleware('auth:sanctum')->get('/user', function (Request $request) {
    return $request->user();
});
