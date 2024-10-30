package com.crud.exception;

public class DuplicateUserException extends RuntimeException{

  private static final long serialVersionUID = 1L;

  public DuplicateUserException() {
    super("Erro no registro, já existe um usuário para este email");
  }
}