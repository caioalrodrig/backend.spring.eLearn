package com.crud.config.oauth2;

import java.util.Optional;
import java.util.Map;

import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.crud.dto.mapper.SignupMapper;
import com.crud.exception.ProcessingOAuth2ServiceException;
import com.crud.model.user.User;
import com.crud.model.user.UserProvider;
import com.crud.repository.UserRepository;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

  private UserRepository userRepository;
  private SignupMapper signupMapper;

  public CustomOAuth2UserService(UserRepository userRepository, SignupMapper signupMapper){
    this.userRepository = userRepository;
    this.signupMapper = signupMapper;
  }

  @Override
  public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
    OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

    try {
      return processOAuth2User(oAuth2UserRequest, oAuth2User);
    } catch (AuthenticationException ex) {
      throw ex;
    } catch (Exception ex) {
      throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
    }
  }

  private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
    
    Map<String, Object> oAuth2UserInfo = oAuth2User.getAttributes();

    if (oAuth2UserInfo.get("email") == null) {
      throw new ProcessingOAuth2ServiceException();
    }

    Optional<User> userOptional = userRepository.findByEmail((String) oAuth2UserInfo.get("email"));
    User user;
    if (userOptional.isPresent()) {
      user = userOptional.get();
      if (!user.getProvider().equals(UserProvider.valueOf(
          oAuth2UserRequest.getClientRegistration().getRegistrationId()))) {
        throw new ProcessingOAuth2ServiceException(user.getProvider());
      }
      user = updateExistingUser(user, oAuth2UserInfo);
      } else {
      user = userRepository.save(signupMapper.toEntity(oAuth2User, oAuth2UserRequest.getClientRegistration().getRegistrationId()));
    }
    return user;
  }  

  private User updateExistingUser(User existingUser, Map<String, Object> oAuth2UserInfo) {
    
    existingUser.setName((String)oAuth2UserInfo.get("name"));
    existingUser.setImageUrl((String)oAuth2UserInfo.get("picture"));

    return userRepository.save(existingUser);
  }

}