package com.crud.config.auth;

import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.crud.model.user.User;
import com.crud.repository.UserRepository;
import com.crud.util.TokenService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TokenAuthFilter extends OncePerRequestFilter {
  @Autowired
  private TokenService tokenService;

  @Autowired
  private UserRepository userRepository;

  private static final Logger logger = LoggerFactory.getLogger(TokenAuthFilter.class);

  @Override
  protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
    throws ServletException, IOException {
      try {
        String jwt = getJwtFromRequest(request);

        if (StringUtils.hasText(jwt) && tokenService.validateToken(jwt)) {
          UUID userId = tokenService.getUserIdFromToken(jwt);

          Optional<User> oAuth2User = userRepository.findById(userId);
          if (oAuth2User.isPresent()) {

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(oAuth2User,
              null, oAuth2User.get().getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
          }
        }
      } catch (Exception ex) {
        logger.error("Could not set user authentication in security context", ex);
      }

      filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
      String bearerToken = request.getHeader("Authorization");
      if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
          return bearerToken.substring(7, bearerToken.length());
      }
      return null;
    }
}