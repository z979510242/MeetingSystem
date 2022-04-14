package csu.software.meetingsystem.exception;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public class ApiError {
    private final ZonedDateTime timestamp;
    private final String message;
    private final HttpStatus status;

    public ApiError(ZonedDateTime timestamp, String message, HttpStatus status) {
        this.timestamp = timestamp;
        this.message = message;
        this.status = status;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
