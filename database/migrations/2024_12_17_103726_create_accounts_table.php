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
        Schema::create('accounts', function (Blueprint $table) {
            $table->id('id_account');
            $table->string('email', 50);
            $table->string('password', 250);
            $table->integer('attempt')->default(0);
            $table->integer('max_attempt')->default(3);
            $table->unsignedBigInteger('id_pending_register');
            $table->unsignedBigInteger('id_type_account_state');
            $table->timestamps();

            $table->unique('id_pending_register');
            $table->foreign('id_pending_register')->references('id_pending_register')->on('pending_registers');
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
        Schema::dropIfExists('accounts');
    }
};
