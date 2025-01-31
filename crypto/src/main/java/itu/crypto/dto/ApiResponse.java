package itu.crypto.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class ApiResponse {
    private String status;
    private String message;
    private Object data;
    private Object errors;

    public ApiResponse(String status, String message, Object data, Object errors) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.errors = errors;
    }

    public boolean isOk() {
        return !isError() && "success".equals(status);
    }

    public boolean isError() {
        return "error".equals(status) || errors != null;
    }
}
