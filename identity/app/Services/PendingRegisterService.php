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

        if (!self::verifyPin($pending_register, $pin)) {
            throw new \Exception("Pin invalid");
        }
        $account = self::createAccountFromPendingRegister($pending_register);
        return $account;
    }

    /**
     * Récupérer un Pendingregister par son ID
     *
     * @param int $idPendingRegister
     * @return PendingRegister|null
     */
    public static function getPendingRegisterById(int $idPendingRegister): ?PendingRegister
    {
        return PendingRegister::find($idPendingRegister);
    }

    /**
     * Vérification du pin
     *
     * @param PendingRegister $pendingRegister
     * @param string $pin
     * @return bool
     */
    public static function verifyPin(PendingRegister $pendingRegister, string $pin): bool
    {
        return $pendingRegister->pin === $pin;
    }

    /**
     * Créer un account à partir d'un Pendingregister
     *
     * @param PendingRegister $pendingRegister
     * @return Account
     * @throws \Exception
     */
    public static function createAccountFromPendingRegister(PendingRegister $pendingRegister): Account
    {
        $account = $pendingRegister->validateAccount();
        return $account;
    }
}
