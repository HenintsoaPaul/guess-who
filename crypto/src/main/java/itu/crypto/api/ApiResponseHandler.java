package itu.crypto.api;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

public class ApiResponseHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        // Ne considérer AUCUNE réponse comme une erreur
        return false;
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        // Cette méthode ne sera jamais appelée car hasError() retourne toujours false
    }
}