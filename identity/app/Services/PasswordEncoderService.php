<?php

namespace App\Services;

class PasswordEncoderService
{
    public static function encodePassword($password): string
    {
//        return password_hash($password, PASSWORD_DEFAULT);
        return $password;
    }

    public static function verifyPassword($password, $hash): bool
    {
//        return password_verify($password, $hash);
        return self::encodePassword($password) === $hash;
    }
}
