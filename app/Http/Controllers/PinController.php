<?php

namespace App\Http\Controllers;
use Illuminate\Http\Request;

use App\Mail\SendEmail;
use Illuminate\Support\Facades\Mail;

class PinController extends Controller
{
    public function sendPinCode(Request $request)
    {
        $request->validate([
            'email' => 'required|email'
        ]);

        $pinCode = rand(100000, 999999);

        Mail::to($request->email)->send(new SendEmail($pinCode));

        return response()->json(['message' => 'Code PIN envoyé par email.']);
    }
}
