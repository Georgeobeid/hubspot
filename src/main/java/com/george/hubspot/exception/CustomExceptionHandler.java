package com.george.hubspot.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(OAuthConfigurationException.class)
    public ResponseEntity<Object> handleOAuthConfigurationException(OAuthConfigurationException ex) {
        CustomErrorResponse customErrorResponse = new CustomErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(customErrorResponse);
    }

    @ExceptionHandler(OAuthAuthenticationException.class)
    public ResponseEntity<Object> handleOAuthAuthenticationException(OAuthAuthenticationException ex) {
        CustomErrorResponse customErrorResponse = new CustomErrorResponse(HttpStatus.UNAUTHORIZED.value(), ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(customErrorResponse);
    }

    @ExceptionHandler(MissingRequiredParameterException.class)
    public ResponseEntity<Object> handleMissingRequiredParameterException(MissingRequiredParameterException ex) {
        CustomErrorResponse customErrorResponse = new CustomErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(customErrorResponse);
    }

    @ExceptionHandler(TooManyRequestsException.class)
    public ResponseEntity<Object> handleTooManyRequestsException(TooManyRequestsException ex) {
        CustomErrorResponse customErrorResponse = new CustomErrorResponse(HttpStatus.TOO_MANY_REQUESTS.value(), ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(customErrorResponse);
    }

    @ExceptionHandler(ContactCreationException.class)
    public ResponseEntity<Object> handleContactCreationException(ContactCreationException ex) {
        CustomErrorResponse customErrorResponse = new CustomErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(customErrorResponse);
    }

}
