<?php

namespace App\Services;

use App\Models\pending_Pendingregister;
use App\Models\account;

class PendingRegisterService
{
    
    public static function validateAccountRegister(int $id_pending_register , string $pin){
        $pending_register = self::getPendingRegisterById($id_pending_register);
        if( !self::verifyPin($pending_register,$pin)){
            throw new \Exception("Pin invalid");
        }
        self::createAccountFromPendingRegister($pending_register);
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
