<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('account_states', function (Blueprint $table) {
            $table->id('id_account_state');
            $table->timestamp('date_state');
            $table->unsignedBigInteger('id_account');
            $table->unsignedBigInteger('id_type_account_state');
            $table->timestamps();

            $table->foreign('id_account')->references('id_account')->on('accounts');
            $table->foreign('id_type_account_state')->references('id_type_account_state')->on('type_account_states');
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('account_states');
    }
};
