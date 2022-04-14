package csu.software.meetingsystem.response;

import org.springframework.http.HttpStatus;

public class LoginResponse {
    private HttpStatus httpStatus;
    private String message;
    private String token;

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LoginResponse() {
    }
}
