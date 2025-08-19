package com.shreyas.SAS.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

        @ExceptionHandler(EmailNotFoundExecption.class)
        public ResponseEntity<String> handleEmailNotFoundException(EmailNotFoundExecption msg) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg.getMessage());
        }
        @ExceptionHandler(IncorrectPasswordException.class)
        public ResponseEntity<String> handleIncorrectPasswordException(IncorrectPasswordException msg) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(msg.getMessage());
        }
        @ExceptionHandler(NotFoundException.class)
        public ResponseEntity<String> handleNotFoundException(NotFoundException msg) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg.getMessage());
        }
        @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception msg) {
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(msg.getMessage());
        }
        @ExceptionHandler(IncorrectOTP.class)
        public ResponseEntity<String> handleIncorrectOTPException(IncorrectOTP msg) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incoorect Password");
        }
}
