package com.crud.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
// import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.crud.config.jwt.TokenAuthFilter;
import com.crud.config.oauth2.CustomOAuth2UserService;
import com.crud.config.oauth2.OAuth2AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity( securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class SecurityConfig {

  @Autowired 
  private CustomOAuth2UserService customOAuth2UserService; 

  @Autowired
  private OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
  
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http   
      .csrf(csrf -> csrf.disable())
      .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
      .formLogin(formLogin -> formLogin.disable())
      .httpBasic(httpBasic -> httpBasic.disable())
      .authorizeHttpRequests(registry -> {
        registry.requestMatchers("/oauth2/**").permitAll();
        registry.anyRequest().authenticated();
      })
      .oauth2Login(oauth2login -> {
        oauth2login.authorizationEndpoint(authorizationEndpoint -> { 
          authorizationEndpoint.baseUri("/oauth2/authorize/*");
        });
        oauth2login.userInfoEndpoint(userInfoEndpoint -> 
          userInfoEndpoint.userService(customOAuth2UserService)
        );
        oauth2login.successHandler(oAuth2AuthenticationSuccessHandler);
      })
      .addFilterBefore(tokenAuthFilter(), UsernamePasswordAuthenticationFilter.class)
      .build();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public TokenAuthFilter tokenAuthFilter() {
    return new TokenAuthFilter();
  }

}