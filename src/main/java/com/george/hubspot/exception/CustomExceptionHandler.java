package com.george.hubspot.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(NaoEncontradoException.class)
    public ResponseEntity<Object> handleNotFoundException(NaoEncontradoException ex) {
        CustomErrorResponse customErrorResponse = new CustomErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(customErrorResponse);
    }

}
