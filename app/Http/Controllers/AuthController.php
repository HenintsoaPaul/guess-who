<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\Hash;
use App\Models\User;

class AuthController extends Controller
{
    public function showLoginForm()
    {
        return view('etudiants.login');
    }

    public function login(Request $request)
    {
        $credentials = $request->validate([
            'email' => 'required|email',
            'password' => 'required',
        ]);

        if (Auth::attempt($credentials)) {
            $user = Auth::user();

            if ($user->role === 'admin') {
                return redirect()->route('etudiants/listeEtudiant')->with('success', 'Bienvenue, ' . $user->name);
            }

            Auth::logout();
            return redirect()->route('etudiants/login')->withErrors('Accès réservé aux étudiants uniquement.');
        }

        return back()->withErrors('Les informations de connexion sont incorrectes.');
    }

    public function logout()
    {
        Auth::logout();
        return redirect()->route('etudiants/login')->with('success', 'Déconnexion réussie.');
    }
}
