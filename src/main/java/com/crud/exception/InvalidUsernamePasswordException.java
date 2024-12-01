package com.crud.exception;

import java.util.Optional;
import com.crud.model.user.UserProvider;

public class InvalidUsernamePasswordException extends RuntimeException{

  private static final long serialVersionUID = 4L;

  public InvalidUsernamePasswordException(Optional<UserProvider> userProvider) {
    super(userProvider != null ? 
      "Parece que sua conta foi criada com login OAuth2. Por favor, faça login pelo botão " 
       + userProvider.get() : "Usuário ou senha inválidos");
  }

}
