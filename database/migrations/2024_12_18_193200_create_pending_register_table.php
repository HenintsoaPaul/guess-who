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
        Schema::create('pending_register', function (Blueprint $table) {
            $table->id('id_pending_register');
            $table->string('email', 50);
            $table->string('password', 250);
            $table->timestamp('date_register');
            $table->timestamp('date_expiration');
            $table->timestamp('date_validation')->nullable();
            $table->string('pin', 250);
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('pending_register');
    }
};
