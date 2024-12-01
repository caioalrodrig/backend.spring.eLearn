package com.crud.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.crud.exception.DuplicateUserException;
import com.crud.exception.InvalidUsernamePasswordException;
import com.crud.exception.RecordNotFoundException;

import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice()
public class ApplicationControllerAdvice {

  public Map<String, String> errorResponse;

  public ApplicationControllerAdvice(){
    this.errorResponse = new HashMap<>();
  }
  
  @ExceptionHandler(RecordNotFoundException.class)
  public ResponseEntity<Map<String, String>> handleException(RecordNotFoundException ex){
    errorResponse.put("error", ex.getMessage()); 
    return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND); 
  }

  @ExceptionHandler(DuplicateUserException.class)
  public ResponseEntity<Map<String, String>> handleException(DuplicateUserException ex) {
    errorResponse.put("error", ex.getMessage()); 
    return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT); 
  }

  @ExceptionHandler(InvalidUsernamePasswordException.class)
  public ResponseEntity<Map<String, String>> handleException(InvalidUsernamePasswordException ex){
    errorResponse.put("error", ex.getMessage()); 
    return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED); 
  }

  @ExceptionHandler ({ConstraintViolationException.class})
  protected ResponseEntity<Object> handleException(ConstraintViolationException ex) {
    errorResponse.put("error", ex.getMessage()); 
    return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY); 
  }

  @ExceptionHandler({ AuthenticationException.class })
  protected ResponseEntity<Object> handleAuthenticationException(AuthenticationException ex) {
    errorResponse.put("error", ex.getMessage()); 
    return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED); 
  }


}
