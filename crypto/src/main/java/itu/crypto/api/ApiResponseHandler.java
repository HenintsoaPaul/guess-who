package itu.crypto.api;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

public class ApiResponseHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse response) {
        // Ne considérer AUCUNE réponse comme une erreur
        return false;
    }

    @Override
    public void handleError(ClientHttpResponse response) {
        // Cette méthode ne sera jamais appelée car hasError() retourne toujours false
    }
}