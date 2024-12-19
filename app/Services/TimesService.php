<?php

namespace App\Services;

use Carbon\Carbon;

/**
 * Service pour gérer les fonctions de dates
 */
class TimesService
{
    /**
     * Génère une nouvelle date en ajoutant un intervalle à une date donnée.
     *
     * @param mixed $date Date initiale (timestamp, instance Carbon ou format compatible).
     * @param int $interval Nombre d'unités de temps à ajouter (en secondes).
     * @return Carbon La nouvelle date sous forme d'instance Carbon.
     */
    public static function generateDate($date, int $interval): Carbon
    {
        // Convertir l'entrée en une instance de Carbon
        $carbonDate = Carbon::parse($date);

        // Ajouter l'intervalle en secondes
        $carbonDate->addSeconds($interval);

        return $carbonDate;
    }
}
