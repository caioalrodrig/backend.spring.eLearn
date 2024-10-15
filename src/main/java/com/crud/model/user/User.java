package com.crud.model.user;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
import lombok.Data;

@Data
@Table(name = "users")
@Entity
public class User implements OAuth2User {

  @Id
  private String id;

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

  private transient Map<String, Object> attributes;
  private transient String imageUrl;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Collections.singletonList(new SimpleGrantedAuthority(authority.name()));
  }

  @Override
  public Map<String, Object> getAttributes() {
    return attributes;
  }

  public void setAttributes(Map<String, Object> attributes) {
    this.attributes = attributes;
  }

  @Override
  public String getName() {
    return id;
  }
}