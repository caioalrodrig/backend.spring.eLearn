package com.crud.model;

import java.util.Collection;
import java.util.List;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Table(name = "users")
@Entity
@EqualsAndHashCode(of = {"id", "password"})
public class User implements UserDetails{

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(nullable = false)
  @NotBlank
  @Length(min = 2, max = 120)
  @JsonIgnore
  private String name;

  @Column(nullable = false, unique = true)
  @Email(message = "Invalid email format")
  private String email;

  @Column(nullable = false)
  @NotBlank
  @Length(min = 8)
  private String password;

  @Column(nullable = false, length = 15)
  @Pattern(regexp = "Active|Inactive|Not-confirmed")
  private String status;

  @Column(nullable = false, length = 15)
  @Pattern(regexp = "Admin|User")
  private String role;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    if(this.role == "Admin") return List.of(new SimpleGrantedAuthority("Admin"), new SimpleGrantedAuthority("User"));
    return List.of(new SimpleGrantedAuthority("User"));
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}