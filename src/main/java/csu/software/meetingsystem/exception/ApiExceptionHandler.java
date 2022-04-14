package csu.software.meetingsystem.exception;

import com.auth0.jwt.exceptions.JWTDecodeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.security.SignatureException;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<Object> handleSignatureException(SignatureException e) {
        // create payload containing exception details
        ApiError apiException = new ApiError(
                ZonedDateTime.now(ZoneId.of("Z")),
                e.getMessage(),
                HttpStatus.BAD_REQUEST
                );
        // return response entity
        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(JWTException.class)
    public ResponseEntity<Object> handleJWTException(JWTException e) {
        ApiError apiError = new ApiError(ZonedDateTime.now(ZoneId.of("Z")),
                e.getMessage(),
                HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(JWTDecodeException.class)
    public ResponseEntity<Object> handleJWTDecodeException(JWTDecodeException e) {
        ApiError apiError = new ApiError(ZonedDateTime.now(ZoneId.of("Z")),
                e.getMessage(),
                HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }
}
