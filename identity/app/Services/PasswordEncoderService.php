<?php

namespace App\Services;

class PasswordEncoderService
{
    public static function encodePassword($password): string
    {
        return password_hash($password, PASSWORD_DEFAULT);
    }

    public static function verifyPassword($password, $hash): bool
    {
        return password_verify($password, $hash);
    }
}
