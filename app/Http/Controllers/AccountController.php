<?php

namespace App\Http\Controllers;

use App\Mail\SendEmail;
use App\Models\Account;
use App\Services\JsonResponseService;
use App\Services\RandomService;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Mail;
use Illuminate\Validation\ValidationException;
use PHPUnit\Exception;


class AccountController extends Controller
{
    private JsonResponseService $jsonResponse;

    public function __construct(JsonResponseService $jsonResponse)
    {
        $this->jsonResponse = $jsonResponse;
    }

    /**
     * Wish to unlock an account.
     *
     * @throws \Exception
     */
    public function unlock(Request $request)
    {
        try {
            $email = $request->validate([
                'email' => 'required|email',
            ]);

            DB::beginTransaction();
            try {
                $account = Account::getByEmail($email);
                $account->unlockAccount();
                DB::commit();

                $data = [
                    'id_account' => $account->id_account
                ];
                return $this->jsonResponse->success('Account unlocked.', $data);
            } catch (\Exception $e) {
                DB::rollBack();
                throw new \Exception("Account not found.");
            }
        } catch (ValidationException $e) {
            return $this->jsonResponse->error('Invalid email.', $e->errors(), 422);
        }
    }

    /**
     * @throws \Exception
     */
    public function changePassword(Request $request)
    {
        try {
            // TODO: verify token
            // ...
            // TODO: verify token

            $email = $request->validate([
                'email' => 'required|email',
                'password' => 'required',
            ]);

            DB::beginTransaction();
            try {
                $account = Account::getByEmail($email);
                $account->unlockAccount();
                DB::commit();

                $data = [
                    'id_account' => $account->id_account
                ];
                return $this->jsonResponse->success('Account unlocked.', $data);
            } catch (\Exception $e) {
                DB::rollBack();
                throw new \Exception("Account not found.");
            }
        } catch (ValidationException $e) {
            return $this->jsonResponse->error('Invalid email.', $e->errors(), 422);
        }
    }
}
