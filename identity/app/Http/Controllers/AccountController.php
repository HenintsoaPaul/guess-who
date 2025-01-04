<?php

namespace App\Http\Controllers;

use App\Mail\SendEmail;
use App\Models\Account;
use App\Models\PendingPwdChange;
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
                throw $e;
            }
        } catch (ValidationException $e) {
            return $this->jsonResponse->error('Invalid email.', $e->errors(), 422);
        }
    }

    /**
     * @throws \Exception
     */
    public function changePassword(Request $request): \Illuminate\Http\JsonResponse
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

            DB::beginTransaction();
            try {
                $account = Account::getById($payload['id_account']);

                $mety = TokenService::isValidBarerToken($account->id_account, $token);
                if (!$mety) {
                    return $this->jsonResponse->tokenError();
                }

                // Send pin to email
                $pin = RandomService::newPin();
                Mail::to($account->email)->send(new SendEmail($pin));

                // Save in pending auth
                $delai = null;
                $pendingPwdChange = PendingPwdChange::addNew($pin, $account->id_account, $payload['new_password'], $delai);

                DB::commit();
                $data = [
                    'id' => $pendingPwdChange->id_pending_pwd_change,
                    'id_account' => $pendingPwdChange->id_account,
                    'pin' => $pendingPwdChange->pin
                ];
                return $this->jsonResponse->success('Password change email validation sent.', $data);
            } catch (\Exception $e) {
                return $this->jsonResponse->error('Invalid account.', $e->errors(), 422);
            }
        } catch (ValidationException $e) {
            return $this->jsonResponse->error('Invalid payload.', $e->errors(), 422);
        }
    }

    /**
     * @throws \Exception
     */
    public function validateChangePassword(Request $request): \Illuminate\Http\JsonResponse
    {
        try {
            $payload = $request->validate([
                'id' => 'required|numeric',
                'pin' => 'required',
            ]);
            $token = TokenService::getBarerToken($request);
            if (!$token) {
                return $this->jsonResponse->tokenError();
            }

            DB::beginTransaction();
            try {
                $pendingPwdChange = PendingPwdChange::getById($payload['id']);

                $account = Account::getById($pendingPwdChange->id_account);
                if (!TokenService::isValidBarerToken($account->id_account, $token)) {
                    return $this->jsonResponse->tokenError();
                }

                if ($payload['pin'] !== $pendingPwdChange->pin) {
                    return $this->jsonResponse->error('Invalid pin.', $payload, 422);
                }

                $pendingPwdChange->validate($account);
                DB::commit();

                $data = [
                    'id_account' => $pendingPwdChange->id_account,
                ];
                return $this->jsonResponse->success('Password change email validation sent.', $data);
            } catch (\Exception $e) {
                return $this->jsonResponse->error('Invalid account.', $e->errors(), 422);
            }
        } catch (ValidationException $e) {
            return $this->jsonResponse->error('Invalid payload.', $e->errors(), 422);
        }
    }
}
