package com.crud.config.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.crud.repository.UserRepository;


@Validated
@Service
public class CustomUserDetailsService implements UserDetailsService{
  
  private final UserRepository userRepository;

  public CustomUserDetailsService(UserRepository userRepository){
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
    return (UserDetails) userRepository.findByEmail(email)
     .orElseThrow(() -> new UsernameNotFoundException(email));
  }

}