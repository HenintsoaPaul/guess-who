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
            "email" => "alice@gmail.com",
            "password" => "mypassword",
            "id_pending_register" => 1,
            "id_type_account_state" => 1,
            "attempt" => 0,
        ]);
        DB::table('account')->insert([
            "email" => "bob@gmail.com",
            "password" => "mypassword",
            "id_pending_register" => 2,
            "id_type_account_state" => 1,
            "attempt" => 0,
        ]);
        DB::table('account')->insert([
            "email" => "charlie@gmail.com",
            "password" => "mypassword",
            "id_pending_register" => 3,
            "id_type_account_state" => 1,
            "attempt" => 0,
        ]);
        DB::table('account')->insert([
            "email" => "diana@gmail.com",
            "password" => "mypassword",
            "id_pending_register" => 4,
            "id_type_account_state" => 1,
            "attempt" => 0,
        ]);
        DB::table('account')->insert([
            "email" => "eve@gmail.com",
            "password" => "mypassword",
            "id_pending_register" => 5,
            "id_type_account_state" => 1,
            "attempt" => 0,
        ]);
        DB::table('account')->insert([
            "email" => "frank@gmail.com",
            "password" => "mypassword",
            "id_pending_register" => 6,
            "id_type_account_state" => 1,
            "attempt" => 0,
        ]);
        DB::table('account')->insert([
            "email" => "grace@gmail.com",
            "password" => "mypassword",
            "id_pending_register" => 7,
            "id_type_account_state" => 1,
            "attempt" => 0,
        ]);
        DB::table('account')->insert([
            "email" => "hank@gmail.com",
            "password" => "mypassword",
            "id_pending_register" => 8,
            "id_type_account_state" => 1,
            "attempt" => 0,
        ]);
        DB::table('account')->insert([
            "email" => "ivy@gmail.com",
            "password" => "mypassword",
            "id_pending_register" => 9,
            "id_type_account_state" => 1,
            "attempt" => 0,
        ]);
        DB::table('account')->insert([
            "email" => "jack@gmail.com",
            "password" => "mypassword",
            "id_pending_register" => 10,
            "id_type_account_state" => 1,
            "attempt" => 0,
        ]);
    }
}
