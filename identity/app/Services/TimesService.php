<?php

namespace App\Services;

use Carbon\Carbon;
use Illuminate\Support\Facades\Config;

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

    /**
     * Génère une date d'expiration pour un register sur la durée configurée
     *
     * @return Carbon
     */
    public static function genExpirationDateForRegister(): Carbon
    {
        $delayInSeconds = Config::get('PENDING_REGISTER_LIFETIME_SECOND', 60 * 5);
        return self::generateDate(now(), $delayInSeconds);
    }

    /**
     * Génère une date d'expiration pour un token sur la durée configurée
     *
     * @return Carbon
     */
    public static function genExpirationDateForToken(): Carbon
    {
        $delayInSeconds = Config::get('SESSION_LIFETIME_SECOND', 60 * 5);
        return self::generateDate(now(), $delayInSeconds);
    }

    /**
     * Génère une date d'expiration pour l'authentification basée sur la durée configurée
     *
     * @return Carbon
     */
    public static function genExpirationDateForAuth(): Carbon
    {
        $delayInSeconds = Config::get('PENDING_AUTH_LIFETIME_SECOND', 90);
        return self::generateDate(now(), $delayInSeconds);
    }

    /**
     * Génère une date d'expiration pour le changement de mot de passe basée sur la durée configurée
     *
     * @return Carbon
     */
    public static function genExpirationDateForPasswordChange(): Carbon
    {
        $delayInSeconds = Config::get('PENDING_PASSWORD_CHANGE_LIFETIME_SECOND', 90);
        return self::generateDate(now(), $delayInSeconds);
    }
}
