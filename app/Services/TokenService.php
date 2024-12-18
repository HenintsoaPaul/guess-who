<?php

namespace App\Services;

use App\Services\TimesService;
use App\Models\Token;
use Illuminate\Support\Facades\DB;
use Illuminate\Http\Request;


/**
 * Service pour gérer les tokens
 */
class TokenService {
    /**
     * Recupere le barer token dans la requet
     * @param Request $request La requete a effectuer
     * @return string le token recuperer dans le header de la requete
     */
    public static function getBarerToken(Request $request) {
        $fulltoken = $request->header('Authorization');
        // Get only the bearer token
        $token = $request->bearerToken();
        return $token;
    }

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
            $tokenModel->date_expiration = self::genExpirationDate();
            // Sauvegarder dans la base de données.
            $tokenModel->save();
            return $tokenModel;
        } catch (\Exception $e) {
            // Gérer les erreurs éventuelles (log ou lancer une exception).
            throw new \RuntimeException("Erreur lors de la génération du token : " . $e->getMessage());
        }
    }

    public static function genExpirationDate(){
        return TimesService::generateDate(now(),3600*5);
    }

    /**
     * Régénère un token pour un utilisateur donné.
     *
     * @param int $id Identifiant de l'utilisateur.
     * @param string $token Le token a regenerer.
     * @param int $length Longueur du nouveau token généré (par défaut : 64).
     * @return string Le nouveau token.
     */
    public static function regenerateBarerToken(int $id, string $token = NULL, int $length = 64): string
    {
        // Controle du token , Utiliser le token valide en base de donnee si token NULL passer en argument
        if($token == NULL){
            $tokenModel = self::getLastUsableToken($id);
            if($tokenModel == NULL){
                throw new \Exception("Token invalid pour le compte : ".$id." et token : ".$token);
            }
            $token = $tokenModel->token;
        }
        try {
            // Générer un nouveau token.
            $newToken = self::newToken($length);
    
            // Mise à jour du token dans la base de données avec les conditions sur `id_account` et `token`.
            $updated = DB::table('tokens')
                ->where('id_account', $id)
                ->where('token', $token)
                ->update([
                    'token' => $newToken,
                    'date_expiration' => self::genExpirationDate()
                ]);
    
            // Vérifie si la mise à jour a été effectuée.
            if ($updated === 0) {
                throw new \RuntimeException("Aucun enregistrement trouvé avec cet id_account et token.");
            }
            return $newToken;
        } catch (\Exception $e) {
            // Gérer les erreurs éventuelles (log ou lancer une exception).
            throw new \RuntimeException("Erreur lors de la régénération du token : " . $e->getMessage());
        }
    }
    /**
     * Regenerer un token pour un utilisateur a partir du token dans le header de la requete
     * @param int $id L'identifiant du compte de l'utilisateur
     * @param Request $request La requete a effectuer
     * @return string Le nouveau token generer
     */
    public static function regenerate(int $id, Request $request): string
    {
        $token = self::getBarerToken($request);
        return self::regenerateToken($id,$token);
    }

    /**
     * Recuperer le model du dernier token valide cree.
     * 
     * @param int $id_account L'identifiant pour un account d'un utilisateur.
     * @return Token|null Le model de token utilisable correspondant a l'id_account, ou null si aucun token valide n'a ete trouve.
     */
    public static function getLastUsableToken($id_account)
    {
        // Vérifie que l'identifiant est valide.
        if (empty($id_account) || !is_numeric($id_account)) {
            return null;
        }

        // Récupère le token avec la date d'expiration supérieure à maintenant.
        $tokenModel = Token::where('id_account', $id_account)
            ->where('date_expiration', '>', now())
            ->orderBy('date_expiration', 'desc') // Le dernier token valide créé.
            ->first();

        return $tokenModel;
    }


    /**
     * Vérifie si un token est valide pour un identifiant donné.
     *
     * @param string $identifier Identifiant unique associé au token.
     * @param string $token Token à valider.
     * @param int $expiryTime Durée de validité en secondes (par défaut : 3600 secondes = 1 heure).
     * @return bool `true` si le token est valide, sinon `false`.
     */
    public static function isValidBarerToken(string $id, string $token, int $expiryTime = 3600): bool {
        $tokenModel = self::getLastUsableToken($id);
        if( $tokenModel == NULL || $tokenModel->token != $token){
            return false;
        }
        return true;
    }
    public static function isValid(string $id,Request $request): bool {
        $token = self::getBarerToken($request);
        return self::isValidBarerToken($id,$token);
    }

}
