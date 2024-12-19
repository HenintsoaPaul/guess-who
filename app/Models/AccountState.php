<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class AccountState extends Model
{
    use HasFactory;

    protected $table = 'account_state';
    protected $primaryKey = 'id_account_state';
    public $timestamps = false;

    protected $fillable = [
        'date_state',
        'id_account',
        'id_type_account_state',
    ];

    /**
     * Relation avec le modèle Account.
     * Un état de compte est associé à un compte.
     */
    public function account()
    {
        return $this->belongsTo(Account::class, 'id_account', 'id_account');
    }

    /**
     * Relation avec le modèle TypeAccountState.
     * Un état de compte est associé à un type d'état spécifique.
     */
    public function typeAccountState()
    {
        return $this->belongsTo(TypeAccountState::class, 'id_type_account_state', 'id_type_account_state');
    }

    public function activate()
    {
        $this->id_account_state = 1;
    }

    public function suspend()
    {
        $this->id_account_state = 2;
    }
}
