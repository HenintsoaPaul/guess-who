<?php

namespace Database\Seeders;

use Illuminate\Database\Seeder;
use Illuminate\Support\Facades\DB;

class TypeAccountStateSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        DB::table('type_account_state')->insert([
            'val' => "Active",
        ]);
        DB::table('type_account_state')->insert([
            'val' => "Suspended",
        ]);
    }
}
