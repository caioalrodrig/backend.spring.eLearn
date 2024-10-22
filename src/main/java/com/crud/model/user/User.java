package com.crud.model.user;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;

@Data
@Table(name = "users", uniqueConstraints = {
  @UniqueConstraint(columnNames = "email")
})
@Entity
public class User implements OAuth2User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  @NotBlank
  @Length(min = 2, max = 120)
  private String name;

  @Column(nullable = false, unique = true)
  @Email
  private String email;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private UserProvider provider;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private UserAuthority authority; 

  private transient Collection<? extends GrantedAuthority> authorities; 
  private transient Map<String, Object> attributes;
  private transient String imageUrl;
  private transient String providerId;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.authorities;
  }

  @Override
  public Map<String, Object> getAttributes() {
    return attributes;
  }

  public void setAttributes(Map<String, Object> attributes) {
    this.attributes = attributes;
    this.authorities = Collections
      .singletonList(new SimpleGrantedAuthority("USER"));
  }

  @Override
  public String getName() {
    return providerId;
  }
}