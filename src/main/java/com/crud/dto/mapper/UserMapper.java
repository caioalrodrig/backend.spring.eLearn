package com.crud.dto.mapper;

import org.springframework.stereotype.Component;

import com.crud.dto.UserDTO;
import com.crud.model.user.User;

@Component
public class UserMapper{

  public UserDTO toDTO(User user, String token){
    return new UserDTO(user.getId(), user.getName(), user.getStatus(), user.getRole(), token);
  }

}