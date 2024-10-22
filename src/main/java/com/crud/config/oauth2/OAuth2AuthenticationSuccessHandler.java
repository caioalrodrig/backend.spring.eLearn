package com.crud.config.oauth2;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.crud.config.AppProperties;
import com.crud.config.jwt.TokenService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

  private TokenService tokenService;

  private AppProperties appProperties;

  private String redirectUrl;

  OAuth2AuthenticationSuccessHandler(TokenService tokenService, AppProperties appProperties) {
    this.tokenService = tokenService;
    this.appProperties = appProperties;
    this.redirectUrl = this.appProperties.getAuth().getRedirectUrl();
  }

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
    
    String token = tokenService.createToken(authentication);

    String redirectUrl = this.redirectUrl + "?token=" + URLEncoder.encode(token, StandardCharsets.UTF_8.name());

    getRedirectStrategy().sendRedirect(request, response, redirectUrl);
  }

}