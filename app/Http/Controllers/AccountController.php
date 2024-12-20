<?php

namespace App\Http\Controllers;

use App\Mail\SendEmail;
use App\Models\Account;
use App\Services\JsonResponseService;
use App\Services\RandomService;
use App\Services\TokenService;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Mail;
use Illuminate\Validation\ValidationException;


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
            $payload = $request->validate([
                'id_account' => 'required|numeric',
                'new_password' => 'required',
            ]);
            $token = TokenService::getBarerToken($request);
            if (!$token) {
                return $this->jsonResponse->tokenError();
            }

            $account = null;
            try {
                $account = Account::getById($payload['id_account']);

                $mety = TokenService::isValidBarerToken($account->id_account, $token);
                if (!$mety) {
                    return $this->jsonResponse->tokenError();
                }

                DB::beginTransaction();

                $pin = RandomService::newPin();
                Mail::to($account->email)->send(new SendEmail($pin));

                // todo: save in pending auth
                // ...

                return $this->jsonResponse->success('Password change email validation sent.', null);
            } catch (\Exception $e) {
                return $this->jsonResponse->error('Invalid account.', $e->errors(), 422);
            }

            DB::beginTransaction();
            try {
                $account = $token->getAccount();
                $account->changePassword($payload['new_password']);
                DB::commit();

                $data = [
                    'id_account' => $account->id_account
                ];
                return $this->jsonResponse->success('Account unlocked.', $data);
            } catch (\Exception $e) {
                DB::rollBack();
                throw new \Exception("Error on password update.");
            }
        } catch (ValidationException $e) {
            return $this->jsonResponse->error('Invalid payload.', $e->errors(), 422);
        }
    }
}
