package csu.software.meetingsystem.exception;

import org.springframework.http.HttpStatus;

public class JWTException extends Exception {
    private HttpStatus httpStatus;

    public JWTException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}
