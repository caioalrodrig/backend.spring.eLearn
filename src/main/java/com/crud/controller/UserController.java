package com.crud.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.crud.dto.SigninDTO;
import com.crud.dto.SignupDTO;
import com.crud.dto.UserDTO;
import com.crud.service.UserService;

@RestController
@RequestMapping("/auth")
public class UserController {

  private final UserService userService;
  public UserController(UserService userService){
    this.userService = userService;
  }

  @PostMapping("/signup")
  @ResponseStatus(HttpStatus.CREATED)
  public UserDTO userSignup(@RequestBody SignupDTO userDTO){

    return userService.create(userDTO);
  }

  @PostMapping("/signin")
  public UserDTO userSignin(@RequestBody SigninDTO signinDTO){
  
    return userService.signin(signinDTO); 
  }
}