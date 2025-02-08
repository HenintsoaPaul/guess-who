<?php

namespace Database\Seeders;

use Illuminate\Database\Seeder;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Hash;

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
            'email' => "rocruxappafra-4143@yopmail.com",
            'password' => Hash::make("mypassword"),
            'date_register' => now(),
            'date_expiration' => now()->addDays(30),
            'date_validation' => now(),
            'pin' => '123456'
        ]);
        DB::table('pending_register')->insert([
            'email' => "frucodillefeu-1226@yopmail.com",
            'password' => Hash::make("mypassword"),
            'date_register' => now(),
            'date_expiration' => now()->addDays(30),
            'pin' => '123456'
        ]);
        DB::table('pending_register')->insert([
            'email' => "giyoibrappoihou-3938@yopmail.com",
            'password' => Hash::make("mypassword"),
            'date_register' => now(),
            'date_expiration' => now()->addDays(30),
            'date_validation' => now(),
            'pin' => '123456'
        ]);
        DB::table('pending_register')->insert([
            'email' => "barauxinulli-4329@yopmail.com",
            'password' => Hash::make("mypassword"),
            'date_register' => now(),
            'date_expiration' => now()->addDays(30),
            'pin' => '123456'
        ]);
        DB::table('pending_register')->insert([
            'email' => "meiwoussadate-5662@yopmail.com",
            'password' => Hash::make("mypassword"),
            'date_register' => now(),
            'date_expiration' => now()->addDays(30),
            'date_validation' => now(),
            'pin' => '123456'
        ]);
        DB::table('pending_register')->insert([
            'email' => "boifoissecoullu-3728@yopmail.com",
            'password' => Hash::make("mypassword"),
            'date_register' => now(),
            'date_expiration' => now()->addDays(30),
            'pin' => '123456'
        ]);
        DB::table('pending_register')->insert([
            'email' => "yettennevuffu-9049@yopmail.com",
            'password' => Hash::make("mypassword"),
            'date_register' => now(),
            'date_expiration' => now()->addDays(30),
            'date_validation' => now(),
            'pin' => '123456'
        ]);
        DB::table('pending_register')->insert([
            'email' => "baviwagrouye-6787@yopmail.com",
            'password' => Hash::make("mypassword"),
            'date_register' => now(),
            'date_expiration' => now()->addDays(30),
            'pin' => '123456'
        ]);
        DB::table('pending_register')->insert([
            'email' => "bappoppotameu-6100@yopmail.com",
            'password' => Hash::make("mypassword"),
            'date_register' => now(),
            'date_expiration' => now()->addDays(30),
            'date_validation' => now(),
            'pin' => '123456'
        ]);
        DB::table('pending_register')->insert([
            'email' => "nopeummeuxoigrau-1855@yopmail.com",
            'password' => Hash::make("mypassword"),
            'date_register' => now(),
            'date_expiration' => now()->addDays(30),
            'pin' => '123456'
        ]);
    }
}
