<?php

namespace App\Services;

use App\Models\Account;

class AuthService
{
    protected PasswordEncoderService $passwordEncoderService;

    public function __construct(PasswordEncoderService $passwordEncoderService) {
        $this->passwordEncoderService = $passwordEncoderService;
    }

    /**
     * Verifier que l'email est relie a un compte qui existe, et qu'il n'est pas bloque.
     * Verifier que le password est correct. Diminuer les attempts, en cas d'erreur.
     *
     * @throws \Exception
     */
    public static function authenticate(string $email, string $password): Account
    {
        $account = Account::where('email', $email)->first();

        if (!$account) {
            throw new \Exception('Account not found');
        }

        if ($account->attempt === $account->max_attempt) {
            throw new \Exception('Max attempt reached');
        }

        if (!$account->id_type_account_state === 2) {
            throw new \Exception('Account suspended!');
        }

        $ok_pass = PasswordEncoderService::verifyPassword($password, $account->password);
        if (!$ok_pass) {
            $reste = $account->increaseAttempt();
            throw new \Exception("Wrong password! $reste attempts left!");
        }

        return $account;
    }
}
