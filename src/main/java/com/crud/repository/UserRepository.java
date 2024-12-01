package com.crud.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import com.crud.model.user.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByEmail(String email);

}