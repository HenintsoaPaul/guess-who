<?php

namespace App\Services;

use App\Models\PendingRegister;
use App\Models\Account;

class PendingRegisterService
{

    /**
     * @throws \Exception
     */
    public static function validateAccountRegister(string $email, string $pin): Account
    {
        $pending_register = PendingRegister::where('email', $email)->first();

        // Verifier que l'inscription a ete effectuer
        if ($pending_register == null) {
            throw new \Exception("PendingRegister introuvable. Veuillez vous enregister.");
        }

        // Verifier que l'inscription n'est pas encore valider
        if ($pending_register->date_validation != null) {
            throw new \Exception("Inscription deja valider.");
        }

        // Verifier que l'inscription n'est pas encore expiree
        if ($pending_register->isExpired()) {
            throw new \Exception("Inscription expiree. Enregistrez-vous de nouveau pour reinitialiser.");
        }

        if ($pending_register->pin !== $pin) {
            throw new \Exception("Pin invalid");
        }

        return $pending_register->validateAccount();
    }
}
