<?php

namespace App\Http\Controllers;

use App\Services\JsonResponseService;
use App\Services\RandomService;
use Illuminate\Http\Request;

class RegisterController extends Controller
{
    protected $jsonResponse;
    protected $random;

    public function __construct(JsonResponseService $jsonResponse, RandomService $random)
    {
        $this->jsonResponse = $jsonResponse;
        $this->random = $random;
    }

    public function controlInput(Request $request)
    {
        try {
            $information = $request->validate([
                'email' => 'required|email',
                'password' => 'required|string|min:1',
            ]);
        } catch (\Illuminate\Validation\ValidationException $e) {
            return $this->jsonResponse->error('Les données sont invalides.', $e->errors(), 422);
        }

        return $this->jsonResponse->success('Vous avez recu votre code pin', $this->random->newPin());
    }

}
