package itu.crypto.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ApiResponse<T> {
    private String status;
    private String message;
    private T data;
    private Object errors;

    public boolean isOk() {
        return !isError() && "success".equals(status);
    }

    public boolean isError() {
        return "error".equals(status) || errors != null;
    }
}
