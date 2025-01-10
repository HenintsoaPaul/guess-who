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

// Route::resource('emails', PinController::class);
// Route::post('emails/sendPin', [PinController::class, 'sendPinCode']);

// Docs
Route::get('/api/documentation', function () {
    return view('swagger.index');
});

// Login
Route::post('/login', [LoginController::class, 'login']);
Route::post('/login/validate', [LoginController::class, 'validatePin']);

// Account (unlock + password)
Route::post('/account/unlock', [AccountController::class, 'unlock']);
Route::post('/account/password', [AccountController::class, 'changePassword']);
Route::post('/account/password/validate', [AccountController::class, 'validateChangePassword']);

// Register
Route::post('/register', [RegisterController::class, 'insertRegister']);
Route::post('/register/validate', [RegisterController::class, 'validateRegister']);

// Token
Route::get('token', [TokenController::class, 'index']); // afficher un token généré
Route::get('token/gen/{id_account}', [TokenController::class, 'generate']); // générer un token pour un account
Route::get('token/regen/{id_account}', [TokenController::class, 'regenerate']); // régénérer un token pour un account

Route::get('/validate-token', function (Request $request) {
    $token = $request->header('Authorization');

    if (!$token || !Str::startsWith($token, 'Bearer ')) {
        return response()->json(['error' => 'Invalid token'], 401);
    }

    $token = Str::replaceFirst('Bearer ', '', $token);
    $usermy = \App\Models\Token::where('token', $token)->first();

    if (!$usermy) {
        return response()->json(['error' => 'Token not valid'], 401);
    }

    return response()->json(['valid' => true, 'user' => $usermy]);
});
