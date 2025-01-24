package com.crud.util;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.crud.model.user.User;
import com.crud.model.user.UserStatus;
import com.crud.repository.UserRepository;

@Service
public class EnableUserService {
  private final TokenService tokenService;
  private final UserRepository userRepository;
 
  public EnableUserService(TokenService tokenService, UserRepository userRepository) {
    this.tokenService = tokenService;
    this.userRepository = userRepository;
  }

  public boolean enableUser(String token){
    if (!tokenService.validateToken(token)){
      return false;
    }

    UUID userId = tokenService.getUserIdFromToken(token);
    
    User user = userRepository.findById(userId)
      .orElseThrow(() -> new RuntimeException());

    user.setStatus(UserStatus.enabled);
    userRepository.save(user);
    
    return true;
  }
}
