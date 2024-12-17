<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class TypeAccountState extends Model
{
    use HasFactory;

    protected $table = 'type_account_states';
    protected $primaryKey = 'id_type_account_state';
    public $timestamps = false; 
    protected $fillable = [
        'val',
    ];

    /**
     * Relation avec le modèle Account.
     * Un état de compte peut être utilisé dans plusieurs comptes.
     */
    public function accounts()
    {
        return $this->hasMany(Account::class, 'id_type_account_state', 'id_type_account_state');
    }
}
