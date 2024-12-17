<?php

use Illuminate\Support\Facades\Route;
use App\Http\Controllers\EtudiantController;
use App\Http\Controllers\AuthController;
/*
|--------------------------------------------------------------------------
| Web Routes
|--------------------------------------------------------------------------
|
| Here is where you can register web routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| contains the "web" middleware group. Now create something great!
|
*/
Route::resource('etudiants', EtudiantController::class);

Route::get('etudiants/login', [AuthController::class, 'showLoginForm'])->name('login');
Route::post('etudiants/login', [AuthController::class, 'login']);
Route::get('etudiants/logout', [AuthController::class, 'logout'])->name('logout');

Route::middleware(['etudiants', 'role:admin'])->group(function () {
    Route::get('etudiants/listeEtudiant', function () {
        return view('etudiants.listeEtudiant');
    })->name('etudiants.listeEtudiant');
});


Route::get('/', function () {
    return view('welcome');
});
