<?php

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;
use App\Http\Controllers\PinController;

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

Route::get('emails/sendPin', [PinController::class, 'sendPinCode'])->name('sendPin');

Route::middleware('auth:sanctum')->get('/user', function (Request $request) {
    return $request->user();
});
