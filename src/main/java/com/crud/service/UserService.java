package com.crud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.crud.dto.SigninDTO;
import com.crud.dto.SignupDTO;
import com.crud.dto.UserDTO;
import com.crud.dto.mapper.SignupMapper;
// import com.crud.dto.mapper.SigninMapper;
import com.crud.dto.mapper.UserMapper;
import com.crud.model.User;
import com.crud.repository.UserRepository;

import jakarta.validation.Valid;

@Validated
@Service
public class UserService {

  @Autowired
  AuthenticationManager authenticationManager;

  private final UserRepository userRepository;
  private final SignupMapper signupMapper;
  private final UserMapper userMapper;
  // private final SigninMapper signinMapper;

  public UserService(UserRepository userRepository,
      SignupMapper signupMapper,
      UserMapper userMapper) {
    this.userRepository = userRepository;
    this.signupMapper = signupMapper;
    this.userMapper = userMapper;
    // this.signinMapper = signinMapper;
  }

  public UserDTO create(@Valid SignupDTO signupDTO) {

    return userMapper.toDTO(userRepository.save(signupMapper.toEntity(signupDTO)));
  }

  public UserDTO signin(@Valid SigninDTO signinDTO) {
    var auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signinDTO.email(),
        signinDTO.password()));

    return userMapper.toDTO((User) auth.getPrincipal());
  }
}