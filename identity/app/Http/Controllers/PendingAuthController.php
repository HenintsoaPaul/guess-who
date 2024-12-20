<?php
namespace App\Http\Controllers;

use App\Models\PendingAuth;

class PendingAuthController extends Controller
{
    /**
     * Récupérer un PendingAuth par son ID.
     *
     * @param int $id
     * @return \Illuminate\Http\JsonResponse
     */
    public function getPendingAuthById($id)
    {
        $pendingAuth = PendingAuth::getById($id);

        if (!$pendingAuth) {
            return response()->json(['message' => 'PendingAuth non trouvé'], 404);
        }

        return response()->json($pendingAuth, 200);
    }
}
