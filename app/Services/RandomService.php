<?php 

namespace App\Services;

class RandomService
{
    /**
     * Génère un code PIN aléatoire de 6 chiffres.
     *
     * @return string
     */
    public function newPin(): string
    {
        // Générer un code PIN à 6 chiffres
        return str_pad(rand(0, 999999), 6, '0', STR_PAD_LEFT);
    }
}