<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Support\Facades\Hash;

class Account extends Model
{

    use HasFactory;

    protected $table = 'account';
    protected $primaryKey = 'id_account';
    public $timestamps = false;

    protected $fillable = [
        'email',
        'password',
        'attempt',
        'max_attempt',
        'id_pending_register',
        'id_type_account_state',
    ];

    /**
     * Relation avec le modèle TypeAccountState.
     * Un compte appartient à un état de compte spécifique.
     */
    public function typeAccountState()
    {
        return $this->belongsTo(TypeAccountState::class, 'id_type_account_state', 'id_type_account_state');
    }

    /**
     * Relation avec le modèle PendingRegister.
     * Un compte est lié à une inscription en attente.
     */
    public function pendingRegister()
    {
        return $this->belongsTo(PendingRegister::class, 'id_pending_register', 'id_pending_register');
    }

    public static function authenticate(string $email, string $password)
    {
        $account = self::where('email', $email)->first();

        if (!$account) {
            throw new \Exception('Account not found');
        }

        if (!$account->id_type_account_state === 2) {
            throw new \Exception('Account inactive!');
        }

        if (!$account->id_type_account_state === 3) {
            throw new \Exception('Account suspended!');
        }

        if (!Hash::check($password, $account->password)) {
            throw new \Exception('Wrong password');
            // Diminuer les tentatives
        }

        return $account;
    }
}
