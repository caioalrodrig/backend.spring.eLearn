package com.crud.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.crud.dto.SigninDTO;
import com.crud.dto.SignupDTO;
import com.crud.dto.UserDTO;
import com.crud.dto.mapper.SignupMapper;
import com.crud.dto.mapper.UserMapper;
import com.crud.exception.DuplicateUserException;
import com.crud.exception.InvalidUsernamePasswordException;
import com.crud.model.user.User;
import com.crud.model.user.UserProvider;
import com.crud.repository.UserRepository;
import com.crud.util.TokenService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Validated
@Service
public class UserService {

  @Autowired
  AuthenticationManager authenticationManager;

  private final UserRepository userRepository;
  private final SignupMapper signupMapper;
  private final TokenService tokenService;
  private final UserMapper userMapper;
  private final EmailService emailService;

  public UserService(UserRepository userRepository,
      SignupMapper signupMapper,
      UserMapper userMapper,
      TokenService tokenService,
      EmailService emailService) {
    this.userRepository = userRepository;
    this.signupMapper = signupMapper;
    this.userMapper = userMapper;
    this.tokenService = tokenService;
    this.emailService = emailService;
  }

  @Transactional
  public UserDTO create(@Valid SignupDTO signupDTO) {
    try{
      User user = userRepository.save(signupMapper.toEntity(signupDTO));
      UserDTO userDTO = userMapper.toDTO(user, "");
      
      emailService.create(user);

      return userDTO;
    } catch (Exception e){
      throw new DuplicateUserException();
    }

  }

  public UserDTO signin(@Valid SigninDTO signinDTO) {
    User foundUser = userRepository.findByEmail(signinDTO.email())
     .orElseThrow(() -> new InvalidUsernamePasswordException(null));

    if(foundUser.getProvider() != UserProvider.local){
      throw new InvalidUsernamePasswordException(Optional.ofNullable(foundUser.getProvider()));
    }

    try{
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signinDTO.email(), 
       signinDTO.password()));

      String token = tokenService.createToken(foundUser.getId());
      return userMapper.toDTO(foundUser, token);    
    } catch (Exception e){
      throw new InvalidUsernamePasswordException(null);
    } 

  }
}