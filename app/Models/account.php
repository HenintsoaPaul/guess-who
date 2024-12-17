<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

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
}
