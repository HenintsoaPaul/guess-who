<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;


class UnlockController extends Controller
{
    public function unlockAccounts(Request $request) {
        // if req->id_pending_auth est int
            // $pending_auth = getbyid( req->id )
            // if $pending_auth->pin === $request->pin
            //     pending_auth->getIdaccount()
            //     update attempt =0 

    }
}