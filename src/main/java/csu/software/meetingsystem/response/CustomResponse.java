package csu.software.meetingsystem.response;

import org.springframework.http.HttpStatus;

public class CustomResponse {
    private HttpStatus status;
    private String message;

    public CustomResponse() {
    }

    public CustomResponse(String message, HttpStatus status) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static CustomResponse build(String message, HttpStatus status) {
        return new CustomResponse(message, status);
    }
}
