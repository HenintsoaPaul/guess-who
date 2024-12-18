<?php

namespace App\Http\Controllers;
use Illuminate\Http\Request;
use App\Services\RandomService;
use App\Mail\SendEmail;
use Illuminate\Support\Facades\Mail;

class PinController extends Controller
{
    private $randomService;

    /**
     * Injecte le RandomService dans le contrôleur.
     *
     * @param RandomService $randomService
     */
    public function __construct(RandomService $randomService)
    {
        $this->randomService = $randomService;
    }

    /**
     * Envoie un code PIN à l'email spécifié.
     *
     * @param Request $request
     * @return \Illuminate\Http\JsonResponse
     */
    public function sendPinCode(Request $request)
    {
        $request->validate([
            'email' => 'required|email'
        ]);

        $pinCode = $this->randomService->newPin();

        Mail::to($request->email)->send(new SendEmail($pinCode));

        return response()->json(['message' => 'Code PIN envoyé par email.']);
    }
}
