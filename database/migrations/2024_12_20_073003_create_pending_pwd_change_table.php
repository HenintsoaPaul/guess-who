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
        Schema::create('pending_pwd_change', function (Blueprint $table) {
            $table->id('id_pending_pwd_change');
            $table->timestamp('date_expiration');
            $table->timestamp('date_creation');
            $table->timestamp('date_validation');
            $table->string('pin', 50);
            $table->unsignedBigInteger('id_account');
            $table->unsignedBigInteger('new_password');
            $table->timestamps();

            $table->foreign('id_account')->references('id_account')->on('account');
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('pending_pwd_change');
    }
};
