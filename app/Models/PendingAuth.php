<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class PendingAuth extends Model
{
    use HasFactory;

    protected $table = 'pending_auths';
    protected $primaryKey = 'id_pending_auth';
    public $timestamps = false;

    protected $fillable = [
        'date_expiration',
        'date_creation',
        'pin',
        'id_account',
    ];

    /**
     * Relation avec le modèle Account.
     * Une authentification en attente appartient à un compte.
     */
    public function account()
    {
        return $this->belongsTo(Account::class, 'id_account', 'id_account');
    }

    public static function getById($id)
    {
        return self::find($id);
    }

    /**
     * @throws \Exception
     */
    public static function addNew(string $pin, int $idAccount) {
        $daty = new \DateTime();
        $lifeTime = new \DateInterval('PT90S'); // Delai de 90 secondes

        $pendingAuth = new PendingAuth();
        $pendingAuth->date_creation = $daty;
        $pendingAuth->date_expiration = $daty->add($lifeTime);
        $pendingAuth->pin = $pin;
        $pendingAuth->id_account = $idAccount;

        if (!$pendingAuth->save()) {
            throw new \Exception("Failed to insert pending auth");
        }

        return $pendingAuth;
    }
}
