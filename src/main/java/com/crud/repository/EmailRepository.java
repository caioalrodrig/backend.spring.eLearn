package com.crud.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crud.model.email.Email;

@Repository
public interface EmailRepository extends JpaRepository<Email, UUID> {
  
}
