<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class PendingRegister extends Model
{
    use HasFactory;

    protected $table = 'pending_register';
    protected $primaryKey = 'id_pending_register';
    public $timestamps = false;

    protected $fillable = [
        'email',
        'password',
        'date_register',
        'date_expiration',
        'date_validation',
        'pin',
    ];

    /**
     * Relation avec le modèle Account.
     * Un enregistrement en attente peut être associé à un compte.
     */
    public function account()
    {
        return $this->hasOne(Account::class, 'id_pending_register', 'Id_pending_register');
    }

    public function generateAccount()
    {
        $account = new Account();
        $account->email = $this->email;
        $account->password = $this->password; // Deja hashed
        $account->id_pending_register = $this->id_pending_register;
        $account->id_type_account_state = 1;
        $account->save();

        return $account;
    }

    public function validate($date_validation = null)
    {
        if ($date_validation == null) {
            $date_validation = now();
        }
        $this->date_validation = $date_validation;
        $this->save();
    }


    public function validateAccount()
    {
        $account = $this->generateAccount();
        $this->validate();
        return $account;
    }

    public function isExpired()
    {
        return now()->isAfter($this->date_expiration);
    }
}
