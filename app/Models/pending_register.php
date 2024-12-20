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

    public function account()
    {
        return $this->hasOne(Account::class, 'id_pending_register', 'Id_pending_register');
    }
}
