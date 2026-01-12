package com.example.project.saloon.gentlemanChair.error;

import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.csrf.CsrfException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.management.relation.RoleNotFoundException;
import javax.naming.AuthenticationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiError> handleUsernameNotFoundException(UsernameNotFoundException ex){
        ApiError apiError = new ApiError(ex.getMessage(), HttpStatus.NOT_FOUND);

        return  new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ApiError> handleJwtException(JwtException ex) {
        ApiError apiError = new ApiError("Jwt Validation Failed " + ex.getMessage(), HttpStatus.FORBIDDEN);

        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler(CsrfException.class)
    public ResponseEntity<ApiError> handleCsrfException(CsrfException ex) {
        ApiError apiError = new ApiError("CSRF Validation Failed " + ex.getMessage(), HttpStatus.FORBIDDEN);

        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiError> handleAuthenticationException(AuthenticationException ex) {
        ApiError apiError = new ApiError("Authentication Failed " + ex.getMessage(), HttpStatus.UNAUTHORIZED);

        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiError> handleAccessDeniedException(AccessDeniedException ex) {
        ApiError apiError = new ApiError("Access denied: Insufficient permissions", HttpStatus.FORBIDDEN);

        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleMethodArgumentNotValidException(Exception ex){
        ApiError apiError = new ApiError("Method argument is holding Invalid Property", HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<ApiError> handleRoleNotFoundException(Exception ex){
        ApiError err = new ApiError("Handle Role Properly "+ex.getMessage(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(err, err.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleException(Exception ex) {
        ApiError apiError = new ApiError("An unexpected error occur " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<>(apiError, apiError.getStatus());
    }


}
