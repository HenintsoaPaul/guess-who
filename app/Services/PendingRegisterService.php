<?php

namespace App\Services;

use App\Models\pending_Pendingregister;
use App\Models\account;

class PendingRegisterService
{
    /**
     * Récupérer un Pendingregister par son ID
     *
     * @param int $idPendingRegister
     * @return PendingRegister|null
     */
    public function getPendingRegisterById(int $idPendingRegister): ?PendingRegister
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
    public function verifyPin(PendingRegister $pendingRegister, string $pin): bool
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
    public function createAccountFromPendingRegister(PendingRegister $pendingRegister): Account
    {
        $account = $pendingRegister->validateAccount();
        return $account;
    }
}
