package com.crud.pgsql.crud_pgsql;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import com.crud.dto.SignupDTO;
import com.crud.dto.mapper.SignupMapper;
import com.crud.model.user.User;
import com.crud.repository.UserRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(SignupMapper.class)
public class AuthIntegrationTests {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private SignupMapper signupMapper;

	private User jose;

	@BeforeEach
	public void setUp() {
		SignupDTO joseDTO = new SignupDTO(null, "jose", "jose@gmail.com", "jose1234");
		jose = signupMapper.toEntity(joseDTO);
		entityManager.persistAndFlush(jose);
	}

	@Test
	public void whenFoundByEmail_thenReturnUser() {

		Optional<User> found = userRepository.findByEmail(jose.getEmail());
		assertThat(found).isPresent().hasValueSatisfying(user -> 
			assertThat(user.getEmail()).isEqualTo(jose.getEmail())
		);				
	}

	@Test
	public void whenNotFoundByEmail_thenThrowsException() {
		
		Optional<User> found = userRepository.findByEmail(jose.getEmail());
		assertThat(found).isPresent().hasValueSatisfying(user -> 
			assertThat(user.getEmail()).isNotEqualTo("pedro@gmail.com")
		);				
	}

}