package com.crud.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.crud.config.auth.TokenAuthFilter;
import com.crud.config.oauth2.CustomOAuth2UserService;
import com.crud.config.oauth2.OAuth2AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Autowired 
  private CustomOAuth2UserService customOAuth2UserService; 

  @Autowired
  private OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
  
  @Bean
  @Order(2)
  public SecurityFilterChain oauth2FilterChain(HttpSecurity http) throws Exception {
    return http   
      .csrf(csrf -> csrf.disable())
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
  @Order(1)
  public SecurityFilterChain authBasicFilterChain(HttpSecurity http) throws Exception {
    return http   
      .csrf(csrf -> csrf.disable())
      .cors(cors -> cors.configurationSource(corsConfigurationSource()))
      .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
      .formLogin(formLogin -> formLogin.disable())
      .securityMatcher(AntPathRequestMatcher.antMatcher("/auth/**"))
      .authorizeHttpRequests( auth -> {
        auth.requestMatchers(AntPathRequestMatcher.antMatcher("/auth/**")).permitAll();
        auth.requestMatchers(HttpMethod.OPTIONS,"/**").permitAll();
        auth.requestMatchers(HttpMethod.POST, "auth/signin").permitAll();
        auth.requestMatchers(HttpMethod.POST, "auth/signup").permitAll();
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

@Bean
  public PasswordEncoder passwordEncoder(){
    return new BCryptPasswordEncoder();
  }
  
  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration corsConfiguration = new CorsConfiguration();
    corsConfiguration.setAllowedOrigins(List.of("http://localhost:3000"));
    corsConfiguration.setAllowedMethods(List.of("GET", "POST"));
    corsConfiguration.setAllowCredentials(true);
    corsConfiguration.setAllowedHeaders(List.of("*"));
    corsConfiguration.setMaxAge(3600L);
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", corsConfiguration);
    return source;
  }

}