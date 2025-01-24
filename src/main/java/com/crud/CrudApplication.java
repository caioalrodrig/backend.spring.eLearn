package com.crud;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import com.crud.config.AppProperties;
// import com.crud.model.Course;
// import com.crud.model.email.Email;
// import com.crud.model.email.EmailStatus;
// import com.crud.model.user.User;
// import com.crud.model.user.UserAuthority;
// import com.crud.model.user.UserStatus;
import com.crud.repository.CourseRepository;
import com.crud.repository.EmailRepository;
import com.crud.repository.UserRepository;


@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class CrudApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrudApplication.class, args);
		
	}

	@Bean
	CommandLineRunner initDatabase(CourseRepository courseRepository, 
								UserRepository userRepository, EmailRepository emailRepository){
		return args -> {
			// courseRepository.deleteAll();
			// emailRepository.deleteAll();
			// userRepository.deleteAll();

			// Course c = new Course();
			// c.setName("Angular com Go");
			// c.setCategory("full stack");
			// c.setStatus("Ativo");

			// courseRepository.save(c);

			// User u = new User();
			// u.setName("Jose");
			// u.setEmail("jose@gmail.com");
			// u.setAuthority(UserAuthority.USER);
			// u.setStatus(UserStatus.notConfirmed);

			// User savedUser = userRepository.save(u);

			// Email e = new Email();
			// e.setUser(savedUser);
			// e.setStatus(EmailStatus.PROCESSING);
			// e.setToken("6asdafffs77kmf3kdfmb5çn-okt8e4934krg1oks87o3g1skg");

			// emailRepository.save(e);

		};
	}
}
