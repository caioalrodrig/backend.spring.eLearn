package com.crud.util;

import com.crud.config.AppProperties;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class TokenService {

  private static final Logger logger = LoggerFactory.getLogger(TokenService.class);

  private AppProperties appProperties;

  private String jwtSecret;
  private long expTime;

  public TokenService(AppProperties appProperties) {
    this.appProperties = appProperties;
    this.jwtSecret = this.appProperties.getAuth().getTokenSecret();
    this.expTime = this.appProperties.getAuth().getTokenExpirationMsec();
  }

  public String createToken(UUID userId) {

    Date now = new Date();
    return Jwts.builder()
      .setSubject(userId.toString())
      .setIssuedAt(now)
      .setExpiration(new Date(now.getTime() + expTime))
      .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes())) 
      .compact();
  }

  public UUID getUserIdFromToken(String token) {

    Claims claims = Jwts.parserBuilder()
      .setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
      .build()
      .parseClaimsJws(token)
      .getBody();

    return UUID.fromString(claims.getSubject()); 
  }

  public boolean validateToken(String token) {

    try {
      Jwts.parserBuilder()
        .setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
        .build()
        .parseClaimsJws(token);
      return true;
    }
    catch (MalformedJwtException ex) {
      logger.error("Invalid JWT token");
    } catch (ExpiredJwtException ex) {
      logger.error("Expired JWT token");
    } catch (UnsupportedJwtException ex) {
      logger.error("Unsupported JWT token");
    } catch (IllegalArgumentException ex) {
      logger.error("JWT claims string is empty.");
    }
    return false;
  }
}
