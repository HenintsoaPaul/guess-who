<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Token extends Model
{
    use HasFactory;

    protected $table = 'tokens';
    protected $primaryKey = 'id_token';
    public $timestamps = false; 

    protected $fillable = [
        'date_expiration',
        'token',
        'id_account',
    ];

    /**
     * Relation avec le modèle Account.
     * Un jeton appartient à un compte.
     */
    public function account()
    {
        return $this->belongsTo(Account::class, 'id_account', 'id_account');
    }
}
