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
            'email' => "locked@gmail.com",
            'password' => "mypassword",
            'date_register' => now(),
            'date_expiration' => now()->addDays(30),
            'date_validation' => now(),
            'pin' => 'dadan'
        ]);

        DB::table('pending_register')->insert([
            'email' => "henintsoapaul@gmail.com",
            'password' => "mypassword",
            'date_register' => now(),
            'date_expiration' => now()->addDays(30),
            'pin' => 'damnubro'
        ]);
    }
}
