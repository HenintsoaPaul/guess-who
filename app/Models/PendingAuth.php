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

}
