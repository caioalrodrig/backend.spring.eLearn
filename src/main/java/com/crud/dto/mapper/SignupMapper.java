package com.crud.dto.mapper;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.crud.dto.SignupDTO;
import com.crud.model.user.User;

@Component
public class SignupMapper {

  public User toEntity(SignupDTO signupDTO) {
    if (signupDTO == null) {
      return null;
    }

    User user = new User();
    if (signupDTO.id() != null) { 
      user.setId(signupDTO.id());
    }

    user.setName(signupDTO.name());
    user.setEmail(signupDTO.email());
    user.setPassword(new BCryptPasswordEncoder().encode(signupDTO.password()));
    user.setStatus("Not-confirmed");
    user.setRole("User");
    return user;
  }

}