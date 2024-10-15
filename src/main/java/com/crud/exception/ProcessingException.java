package com.crud.exception;

public class ProcessingException extends RuntimeException{

  private static final long serialVersionUID = 1L;

  public ProcessingException(String message) {
      super(message);
  }
}
