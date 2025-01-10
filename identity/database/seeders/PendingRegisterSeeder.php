<?php

namespace Database\Seeders;

use Illuminate\Database\Seeder;
use Illuminate\Support\Facades\DB;

class PendingRegisterSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        DB::table('pending_register')->insert([
            'email' => "alice@gmail.com",
            'password' => "mypassword",
            'date_register' => now(),
            'date_expiration' => now()->addDays(30),
            'date_validation' => now(),
            'pin' => '123456'
        ]);
        DB::table('pending_register')->insert([
            'email' => "bob@gmail.com",
            'password' => "mypassword",
            'date_register' => now(),
            'date_expiration' => now()->addDays(30),
            'pin' => '123456'
        ]);
        DB::table('pending_register')->insert([
            'email' => "charlie@gmail.com",
            'password' => "mypassword",
            'date_register' => now(),
            'date_expiration' => now()->addDays(30),
            'date_validation' => now(),
            'pin' => '123456'
        ]);
        DB::table('pending_register')->insert([
            'email' => "diana@gmail.com",
            'password' => "mypassword",
            'date_register' => now(),
            'date_expiration' => now()->addDays(30),
            'pin' => '123456'
        ]);
        DB::table('pending_register')->insert([
            'email' => "eve@gmail.com",
            'password' => "mypassword",
            'date_register' => now(),
            'date_expiration' => now()->addDays(30),
            'date_validation' => now(),
            'pin' => '123456'
        ]);
        DB::table('pending_register')->insert([
            'email' => "frank@gmail.com",
            'password' => "mypassword",
            'date_register' => now(),
            'date_expiration' => now()->addDays(30),
            'pin' => '123456'
        ]);
        DB::table('pending_register')->insert([
            'email' => "grace@gmail.com",
            'password' => "mypassword",
            'date_register' => now(),
            'date_expiration' => now()->addDays(30),
            'date_validation' => now(),
            'pin' => '123456'
        ]);
        DB::table('pending_register')->insert([
            'email' => "hank@gmail.com",
            'password' => "mypassword",
            'date_register' => now(),
            'date_expiration' => now()->addDays(30),
            'pin' => '123456'
        ]);
        DB::table('pending_register')->insert([
            'email' => "ivy@gmail.com",
            'password' => "mypassword",
            'date_register' => now(),
            'date_expiration' => now()->addDays(30),
            'date_validation' => now(),
            'pin' => '123456'
        ]);
        DB::table('pending_register')->insert([
            'email' => "jack@gmail.com",
            'password' => "mypassword",
            'date_register' => now(),
            'date_expiration' => now()->addDays(30),
            'pin' => '123456'
        ]);
    }
}
