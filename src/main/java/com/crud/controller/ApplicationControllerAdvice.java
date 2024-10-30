package com.crud.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.crud.exception.DuplicateUserException;
import com.crud.exception.RecordNotFoundException;

@RestControllerAdvice()
public class ApplicationControllerAdvice {
  
  @ExceptionHandler(RecordNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public String handleException(RecordNotFoundException ex){
    return ex.getMessage();
  }

  @ExceptionHandler(DuplicateUserException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  public ResponseEntity<Map<String, String>> handleException(DuplicateUserException ex) {
    Map<String, String> errorResponse = new HashMap<>();
    errorResponse.put("error", ex.getMessage()); 
    return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT); 
  }

  // @ExceptionHandler(CredentialMismatchException.class)
  // @ResponseStatus(HttpStatus.UNAUTHORIZED)
  // public String handleSigninException(CredentialMismatchException ex){
  //   return ex.getMessage();
  // }
}
