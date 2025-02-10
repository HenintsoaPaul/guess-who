<?php

namespace Database\Seeders;

use Illuminate\Database\Seeder;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Hash;

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
            "email" => "rocruxappafra-4143@yopmail.com",
            "password" => Hash::make("mypassword"),
            "id_pending_register" => 1,
            "id_type_account_state" => 1,
            "attempt" => 0,
        ]);
        DB::table('account')->insert([
            "email" => "frucodillefeu-1226@yopmail.com",
            "password" => Hash::make("mypassword"),
            "id_pending_register" => 2,
            "id_type_account_state" => 1,
            "attempt" => 0,
        ]);
        DB::table('account')->insert([
            "email" => "giyoibrappoihou-3938@yopmail.com",
            "password" => Hash::make("mypassword"),
            "id_pending_register" => 3,
            "id_type_account_state" => 1,
            "attempt" => 0,
        ]);
        DB::table('account')->insert([
            "email" => "barauxinulli-4329@yopmail.com",
            "password" => Hash::make("mypassword"),
            "id_pending_register" => 4,
            "id_type_account_state" => 1,
            "attempt" => 0,
        ]);
        DB::table('account')->insert([
            "email" => "meiwoussadate-5662@yopmail.com",
            "password" => Hash::make("mypassword"),
            "id_pending_register" => 5,
            "id_type_account_state" => 1,
            "attempt" => 0,
        ]);
        DB::table('account')->insert([
            "email" => "boifoissecoullu-3728@yopmail.com",
            "password" => Hash::make("mypassword"),
            "id_pending_register" => 6,
            "id_type_account_state" => 1,
            "attempt" => 0,
        ]);
        DB::table('account')->insert([
            "email" => "yettennevuffu-9049@yopmail.com",
            "password" => Hash::make("mypassword"),
            "id_pending_register" => 7,
            "id_type_account_state" => 1,
            "attempt" => 0,
        ]);
        DB::table('account')->insert([
            "email" => "baviwagrouye-6787@yopmail.com",
            "password" => Hash::make("mypassword"),
            "id_pending_register" => 8,
            "id_type_account_state" => 1,
            "attempt" => 0,
        ]);
        DB::table('account')->insert([
            "email" => "bappoppotameu-6100@yopmail.com",
            "password" => Hash::make("mypassword"),
            "id_pending_register" => 9,
            "id_type_account_state" => 1,
            "attempt" => 0,
        ]);
        DB::table('account')->insert([
            "email" => "nopeummeuxoigrau-1855@yopmail.com",
            "password" => Hash::make("mypassword"),
            "id_pending_register" => 10,
            "id_type_account_state" => 1,
            "attempt" => 0,
        ]);
    }
}
