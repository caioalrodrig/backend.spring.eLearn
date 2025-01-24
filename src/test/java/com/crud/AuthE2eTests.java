package com.crud;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import com.crud.dto.SigninDTO;
import com.crud.dto.SignupDTO;
import com.crud.model.user.User;

import com.crud.repository.UserRepository;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = CrudApplication.class)
@AutoConfigureMockMvc
@EnableAutoConfiguration(exclude=SecurityAutoConfiguration.class)
@TestPropertySource(locations = "classpath:application-test.properties")
public class AuthE2eTests {
  private static final Logger logger = LoggerFactory.getLogger(AuthE2eTests.class);

  @Autowired
  private MockMvc mvc;

	@Autowired
	private UserRepository userRepository;
 
  @Autowired
  private ObjectMapper objectMapper;
  
  private SignupDTO joseDTO;

  @BeforeEach
	public void setUp() throws Exception{
		joseDTO = new SignupDTO(null, "jose", "jose@test.com.br", "jose1234");
    logger.info("Run tests set up once");
  }

  // @Test()
  // public void Signup_WhenValidInput_ThenSignupUser() throws IOException, Exception {
  //   logger.info("First test ");

  //   mvc.perform(post("/auth/signup")
  //     .contentType(MediaType.APPLICATION_JSON)
  //     .content(objectMapper.writeValueAsString(joseDTO)))
  //     .andExpect(status().isCreated());

  //   Optional<User> found = userRepository.findByEmail(joseDTO.email());
  //   assertThat(found).isPresent().hasValueSatisfying(user -> 
  //    assertThat(user.getEmail()).isEqualTo(joseDTO.email()));
  // }

  @Test
  public void Signup_WhenDuplicateUser_ThenUserConflict() throws IOException, Exception {
    logger.info("Second test");

    mvc.perform(post("/auth/signup")
      .contentType(MediaType.APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(joseDTO)));
    
    mvc.perform(post("/auth/signup")
      .contentType(MediaType.APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(joseDTO)))
      .andExpect(status().isConflict());
  }

  // @DirtiesContext
  // @Test
	// public void Signin_WhenValidInput_ThenOk() throws IOException, Exception {
  //   SignupDTO signupDTO = new SignupDTO(null, "jose", "josepo@test.com.br", "jose1234");
  //   SigninDTO signinDTO = new SigninDTO("jose@test.com.br", "jose1234");

  //   logger.info("Third test ");

  //   mvc.perform(post("/auth/signup")
  //     .contentType(MediaType.APPLICATION_JSON)
  //     .content(objectMapper.writeValueAsString(signupDTO)))
  //     .andExpect(status().isCreated());		


  //   mvc.perform(post("/auth/signin")
  //     .contentType(MediaType.APPLICATION_JSON)
  //     .content(objectMapper.writeValueAsString(signinDTO)))
  //     .andExpect(status().isOk());
  // }

  // @Test
	// public void Signin_WhenUserNotFound_ThenOk() throws IOException, Exception {
  //   logger.info("Forth test ");

  //   mvc.perform(post("/auth/signin")
  //     .contentType(MediaType.APPLICATION_JSON)
  //     .content(objectMapper.writeValueAsString(joseDTO)))
  //     .andExpect(status().isUnauthorized());
  // }

  // @Test
	// public void GetRecord_WhenWithoutCredentials_ThenUnauthorized() throws IOException, Exception {
  //   logger.info("Fifth test ");

  //   mvc.perform(get("/api/course"))
  //     .andExpect(status().isUnauthorized());
  // }
  

}