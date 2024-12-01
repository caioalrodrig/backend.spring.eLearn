package com.crud.exception;

import com.crud.model.user.UserProvider;

public class ProcessingOAuth2ServiceException extends RuntimeException{

  private static final long serialVersionUID = 2L;

  public ProcessingOAuth2ServiceException() {
    super("Email not found from OAuth2 provider");
  }

  public ProcessingOAuth2ServiceException(UserProvider userProvider) {
    super("Looks like you're signed up with " + 
      userProvider + " account. Please use this login to sign in.");
  }
}
