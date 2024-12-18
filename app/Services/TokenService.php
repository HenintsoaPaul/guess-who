<?php

namespace App\Services;

use App\Services\TimesService;
use App\Models\Token;
use Illuminate\Support\Facades\DB;

/**
 * Service pour gérer les tokens
 */
class TokenService {
    private $tokens = []; // Tableau pour stocker les tokens générés.

    /**
     * Génère un nouveau token unique.
     *
     * @param int $length Longueur du token généré (par défaut : 64).
     * @return string Le nouveau token.
     */
    public static function newToken(int $length = 64): string {
        if ($length <= 0) {
            throw new \InvalidArgumentException("La longueur du token doit être un entier positif.");
        }

        // Génère un token unique.
        return bin2hex(random_bytes($length / 2));
    }

    /**
     * Génère et associe un nouveau token à un utilisateur.
     *
     * @param int $id Identifiant de l'utilisateur.
     * @return Token Le modèle de token créé.
     */
    public static function generate(int $id): Token {
        try {
            // Créer une nouvelle instance du modèle Token.
            $tokenModel = new Token();

            // Générer un token unique.
            $tokenModel->id_account = $id;
            $tokenModel->token = self::newToken();
            $tokenModel->date_expiratio = TimesService::generateDate(now(), 1);

            // Sauvegarder dans la base de données.
            $tokenModel->save();

            return $tokenModel;
        } catch (\Exception $e) {
            // Gérer les erreurs éventuelles (log ou lancer une exception).
            throw new \RuntimeException("Erreur lors de la génération du token : " . $e->getMessage());
        }
    }

    /**
     * Régénère un token pour un utilisateur donné.
     *
     * @param int $id Identifiant de l'utilisateur.
     * @param int $length Longueur du nouveau token généré (par défaut : 64).
     * @return string Le nouveau token.
     */
    public static function regenerate(int $id, int $length = 64): string {
        try {
            // Générer un nouveau token.
            $newToken = self::newToken($length);

            // Mise à jour du token dans la base de données.
            DB::table('tokens')->where('user_id', $id)->update([
                'token' => $newToken,
                'expires_at' => TimesService::generateDate(now(), 1)
            ]);

            return $newToken;
        } catch (\Exception $e) {
            // Gérer les erreurs éventuelles (log ou lancer une exception).
            throw new \RuntimeException("Erreur lors de la régénération du token : " . $e->getMessage());
        }
    }

    /**
     * Vérifie si un token est valide pour un identifiant donné.
     *
     * @param string $identifier Identifiant unique associé au token.
     * @param string $token Token à valider.
     * @param int $expiryTime Durée de validité en secondes (par défaut : 3600 secondes = 1 heure).
     * @return bool `true` si le token est valide, sinon `false`.
     */
    public static function isValid(string $identifier, string $token, int $expiryTime = 3600): bool {
        if (!isset($this->tokens[$identifier])) {
            return false;
        }

        // Récupère les détails du token stocké.
        $storedToken = $this->tokens[$identifier];
        if ($storedToken['token'] !== $token) {
            return false;
        }

        // Vérifie si le token a expiré.
        if (time() - $storedToken['created_at'] > $expiryTime) {
            unset($this->tokens[$identifier]); // Supprime le token expiré.
            return false;
        }

        return true;
    }
}
