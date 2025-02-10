package itu.crypto.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class DateService {

    /**
     * Convertir une date de format "09/02/2025 21:49:04 UTC+3" vers un LocalDateTime.
     * Si la date {@code dateStr} est null, retourne null.
     */
    public LocalDateTime strToLocalDateTime(String dateStr) {
        if (dateStr == null) {
            return null;
        }

        dateStr = removeUtcPart(dateStr);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        return LocalDateTime.parse(dateStr, formatter);
    }

    /**
     * Supprime la partie du string à partir de "UTC".
     * @param dateString La chaîne de date à traiter.
     * @return La chaîne de date sans la partie UTC.
     */
    public static String removeUtcPart(String dateString) {
        int utcIndex = dateString.indexOf("UTC");
        if (utcIndex != -1) {
            return dateString.substring(0, utcIndex).trim();
        }
        return dateString;
    }
}
