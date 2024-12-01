package com.crud.dto.mapper;

import java.util.Map;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import com.crud.dto.SignupDTO;
import com.crud.model.user.User;
import com.crud.model.user.UserAuthority;
import com.crud.model.user.UserProvider;
import com.crud.model.user.UserStatus;

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
    user.setProvider(UserProvider.local);
    user.setPassword(new BCryptPasswordEncoder().encode(signupDTO.password()));
    user.setStatus(UserStatus.enabled);
    user.setAuthority(UserAuthority.USER);
    return user;
  }

  public User toEntity(OAuth2User oAuth2User, String providerName) {

    User user = new User();
    Map<String, Object> oAuth2UserInfo = oAuth2User.getAttributes();
    
    user.setProvider(UserProvider.valueOf(providerName));
    user.setProviderId((String)oAuth2UserInfo.get("sub"));
    user.setName((String)oAuth2UserInfo.get("name"));
    user.setEmail((String)oAuth2UserInfo.get("email"));
    user.setImageUrl((String)oAuth2UserInfo.get("picture"));
    user.setAttributes(oAuth2UserInfo);
    user.setStatus(UserStatus.enabled);
    user.setAuthority(UserAuthority.USER); 

    return user;

  }

}