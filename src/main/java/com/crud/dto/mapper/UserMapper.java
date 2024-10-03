package com.crud.dto.mapper;

import org.springframework.stereotype.Component;

import com.crud.dto.UserDTO;
import com.crud.model.User;

@Component
public class UserMapper{

  public UserDTO toDTO(User user){
    return new UserDTO(user.getId(), user.getName(), user.getStatus(), user.getRole());
  }

}