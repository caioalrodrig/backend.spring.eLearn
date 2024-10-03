package com.crud.controller;

import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.crud.exception.RecordNotFoundException;

@RestControllerAdvice()
public class ApplicationControllerAdvice {
  
  @ExceptionHandler(RecordNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public String handleException(RecordNotFoundException ex){
    return ex.getMessage();
  }

  // @ExceptionHandler(CredentialMismatchException.class)
  // @ResponseStatus(HttpStatus.UNAUTHORIZED)
  // public String handleSigninException(CredentialMismatchException ex){
  //   return ex.getMessage();
  // }
}