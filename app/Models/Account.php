<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Support\Facades\DB;

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

    public function remainingAttempt(): int
    {
        return $this->max_attempt - $this->attempt;
    }

    /**
     * @return int The number of remaining attempt(s).
     * @throws \Exception
     */
    public function increaseAttempt(): int
    {
        $res = $this->update([
            'attempt' => $this->attempt + 1,
        ]);
        if (!$res) {
            throw new \Exception('Failed to increase attempt');
        }

        if ($this->attempt === $this->max_attempt) {
            $this->lockAccount();
            DB::commit();
            throw new \Exception('Account locked!');
        }

        return $this->remainingAttempt();
    }

    /**
     * @throws \Exception
     */
    public function lockAccount()
    {
        $a_state = new AccountState();
        $a_state->date_state = new \DateTime();
        $a_state->id_account = $this->id_account;
        $a_state->suspend();

        $res = $a_state->save();
        if (!$res) {
            throw new \Exception('Failed to lock account! Error on insert account_state.');
        }

        $res = $this->update([
            'id_type_account_state' => $a_state->id_type_account_state,
        ]);
        if (!$res) {
            throw new \Exception('Failed to lock account! Error on update column account.id_type_account_state.');
        }
    }

    /**
     * @throws \Exception
     */
    public function unlockAccount()
    {
        $a_state = new AccountState();
        $a_state->date_state = new \DateTime();
        $a_state->id_account = $this->id_account;
        $a_state->activate();

        $res = $a_state->save();
        if (!$res) {
            throw new \Exception('Failed to unlock account! Error on insert account_state.');
        }

        $res = $this->update([
            'id_type_account_state' => $a_state->id_type_account_state,
            'attempt' => 0
        ]);
        if (!$res) {
            throw new \Exception('Failed to unlock account! Error on update column account.id_type_account_state.');
        }
    }

    /**
     * @throws \Exception
     */
    public function resetAttempt()
    {
        $res = $this->update([
            'attempt' => 0
        ]);
        if (!$res) {
            throw new \Exception('Failed to reset attempts! Error on update column account.attempt.');
        }
    }

    public static function getByEmail($email): Account
    {
        return self::where('email', $email)->firstOrFail();
    }
}
