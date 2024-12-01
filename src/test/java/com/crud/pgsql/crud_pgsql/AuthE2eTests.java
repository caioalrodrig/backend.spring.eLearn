package com.crud.pgsql.crud_pgsql;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import com.crud.CrudApplication;
import com.crud.dto.SignupDTO;
import com.crud.model.user.User;
import com.crud.model.user.UserAuthority;
import com.crud.model.user.UserProvider;
import com.crud.model.user.UserStatus;
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

  @Autowired
  private MockMvc mvc;

	@Autowired
	private UserRepository userRepository;
	
  @Autowired
  private ObjectMapper objectMapper;
  
  private SignupDTO joseDTO;

  @BeforeEach
	public void setUp() throws Exception{
		joseDTO = new SignupDTO(null, "jose", "jose@gmail.com", "jose1234");
	}

  @AfterEach
  public void resetDb() {
    userRepository.deleteAll();
  }

  @Test()
  public void Signup_WhenValidInput_ThenSignupUser() throws IOException, Exception {
    mvc.perform(post("/auth/signup")
      .contentType(MediaType.APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(joseDTO)))
      .andExpect(status().isCreated());

    Optional<User> found = userRepository.findByEmail(joseDTO.email());
    assertThat(found).isPresent().hasValueSatisfying(user -> 
     assertThat(user.getEmail()).isEqualTo(joseDTO.email()));
  }

  @Test
  public void Signup_WhenDuplicateUser_ThenUserConflict() throws IOException, Exception {
    mvc.perform(post("/auth/signup")
      .contentType(MediaType.APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(joseDTO)));
    
    mvc.perform(post("/auth/signup")
      .contentType(MediaType.APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(joseDTO)))
      .andExpect(status().isConflict());
  }

  @Test
	public void Signin_WhenNotActualProvider_ThenUnauthorized() throws IOException, Exception {
		
    User oauth2UserMock = new User();
    oauth2UserMock.setProvider(UserProvider.google);
    oauth2UserMock.setProviderId("mock");
    oauth2UserMock.setName("Jose");
    oauth2UserMock.setEmail("jose@gmail.com");
    oauth2UserMock.setImageUrl("imageMockUrl");
    oauth2UserMock.setStatus(UserStatus.enabled);
    oauth2UserMock.setAuthority(UserAuthority.USER); 
    userRepository.save(oauth2UserMock);

    mvc.perform(post("/auth/signin")
      .contentType(MediaType.APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(joseDTO)))
      .andExpect(status().isUnauthorized());			
	}

  @Test
	public void Signin_WhenUserNotFound_ThenUnauthorized() throws IOException, Exception {
    mvc.perform(post("/auth/signin")
      .contentType(MediaType.APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(joseDTO)))
      .andExpect(status().isUnauthorized());
  }

  @Test
	public void GetRecord_WhenWithoutCredentials_ThenUnauthorized() throws IOException, Exception {
    mvc.perform(get("/api/course"))
      .andExpect(status().isUnauthorized());
  }
  

}