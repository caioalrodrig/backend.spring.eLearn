package com.crud.model.email;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import com.crud.model.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table
public class Email implements Serializable{
  private static final long serialVersionUID = 1L;
  
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "user_id")
  private User user;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private EmailStatus status;

  @Column(nullable = false)
  private String token;

  @Column
  private LocalDateTime sendDateEmail;
}
