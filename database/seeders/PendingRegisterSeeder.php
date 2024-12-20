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
            'email' => "henin@gmail.com",
            'password' => bcrypt("mypassword"),
            'date_register' => now(),
            'date_expiration' => now(),
            'pin' => 'damnubro'
        ]);
    }
}
