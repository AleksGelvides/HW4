package org.example.hw4.web.handlers;

import org.example.hw4.exceptions.CommonHWServiceException;
import org.example.hw4.exceptions.EntityNotFoundException;
import org.example.hw4.exceptions.StupidSecurityException;
import org.example.hw4.web.dto.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionResponse> entityNotFound(EntityNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ExceptionResponse(ex.getLocalizedMessage()));
    }

    @ExceptionHandler(StupidSecurityException.class)
    public ResponseEntity<ExceptionResponse> entityNotFound(StupidSecurityException ex) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new ExceptionResponse(ex.getLocalizedMessage()));
    }

    @ExceptionHandler(CommonHWServiceException.class)
    public ResponseEntity<ExceptionResponse> commonException(CommonHWServiceException ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ExceptionResponse(ex.getLocalizedMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> argumentException(MethodArgumentNotValidException ex) {
        StringBuilder errors = new StringBuilder();
        ex.getBindingResult().getAllErrors()
                .forEach(err -> {
                    errors.append(err.getDefaultMessage() + ". ");
                });
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionResponse(errors.toString()));
    }
}
