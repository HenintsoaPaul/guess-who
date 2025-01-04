<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Support\Facades\DB;

class PendingPwdChange extends Model
{
    use HasFactory;

    protected $table = 'pending_pwd_change';
    protected $primaryKey = 'id_pending_pwd_change';
    public $timestamps = false;

    protected $fillable = [
        'date_expiration',
        'date_creation',
        'date_validation',
        'pin',
        'id_account',
        'new_password',
    ];

    /**
     * Relation avec le modèle Account.
     * Une authentification en attente appartient à un compte.
     */
    public function account()
    {
        return $this->belongsTo(Account::class, 'id_account', 'id_account');
    }

    public static function getById(int $id)
    {
        return self::find($id);
    }

    /**
     * Creer une nouvelle instance. Puis fais un insert.
     *
     * @throws \Exception
     */
    public static function addNew(string $pin, int $idAccount, string $pwd, \DateInterval $delai = null): PendingPwdChange
    {
        $daty = new \DateTime();
        $delai = $delai ?? new \DateInterval('PT90S'); // Utilisation de la valeur par défaut si $delai est null

        $pendingAuth = new PendingPwdChange();
        $pendingAuth->date_creation = $daty;
        $pendingAuth->date_expiration = (clone $daty)->add($delai); // Clone $daty pour ne pas modifier l'original
        $pendingAuth->pin = $pin;
        $pendingAuth->id_account = $idAccount;
        $pendingAuth->new_password = $pwd;

        if (!$pendingAuth->save()) {
            throw new \Exception("Failed to insert pending password change");
        }

        return $pendingAuth;
    }

    /**
     * @throws \Exception
     */
    public function validate(Account $account = null): bool
    {
        if (is_null($account)) {
            $account = Account::getById($this->id_account);
        }

        DB::beginTransaction();
        try {
            $res = $this->update([
                'date_validation' => now()
            ]);
            if (!$res) {
                throw new \Exception("Failed to update pending password change");
            }
            $res = $account->resetPassword($this->new_password);
            DB::commit();
            return $res;
        } catch (\Exception $e) {
            DB::rollBack();
            throw $e;
        }
    }
}