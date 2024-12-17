<?php

namespace App\Services;

/**
 * Service pour gerer les tokens
 */

 class TokenService {
    private $tokens = []; // Tableau pour stocker les tokens générés.

    /**
     * Génère un nouveau token.
     * @param int $length Longueur du token généré (par défaut : 64).
     * @return string Le nouveau token.
     */
    public function newToken(int $length = 64): string {
        if ($length <= 0) {
            throw new InvalidArgumentException("La longueur du token doit être un entier positif.");
        }
        // Génère un token unique.
        $token = bin2hex(random_bytes($length / 2));
        return $token;
    }

    /**
     * Régénère un token pour un identifiant donné.
     *
     * @param string $identifier Identifiant unique associé au token.
     * @param int $length Longueur du nouveau token généré (par défaut : 64).
     * @return string Le nouveau token.
     */
    public function regenerate(string $identifier, int $length = 64): string {
        if (!isset($this->tokens[$identifier])) {
            throw new InvalidArgumentException("Aucun token trouvé pour l'identifiant spécifié.");
        }

        // Supprime l'ancien token et génère un nouveau.
        return $this->newToken($identifier, $length);
    }

    /**
     * Vérifie si un token est valide pour un identifiant donné.
     *
     * @param string $identifier Identifiant unique associé au token.
     * @param string $token Token à valider.
     * @param int $expiryTime Durée de validité en secondes (par défaut : 3600 secondes = 1 heure).
     * @return bool `true` si le token est valide, sinon `false`.
     */
    public function isValid(string $identifier, string $token, int $expiryTime = 3600): bool {
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
