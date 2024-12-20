<?php

namespace Database\Seeders;

use Illuminate\Database\Seeder;
use Illuminate\Support\Facades\DB;

class AccountSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        DB::table('account')->insert([
            "email" => "henin@gmail.com",
            "password" => bcrypt("mypassword"),
            "id_pending_register" => 1,
            "id_type_account_state" => 1,
            "attempt" => 2,
        ]);
    }
}
