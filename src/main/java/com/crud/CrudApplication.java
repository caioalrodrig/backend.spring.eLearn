package com.crud;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import com.crud.config.AppProperties;
import com.crud.model.Course;
import com.crud.model.user.User;
import com.crud.repository.CourseRepository;
import com.crud.repository.UserRepository;


@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class CrudApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrudApplication.class, args);
		
	}

	@Bean
	CommandLineRunner initDatabase(CourseRepository courseRepository, UserRepository userRepository){
		return args -> {
			courseRepository.deleteAll();
			userRepository.deleteAll();

			Course c = new Course();
			c.setName("Angular com Go");
			c.setCategory("full stack");
			c.setStatus("Ativo");

			courseRepository.save(c);

			User u = new User();
			u.setName("Caio Jr");
			u.setEmail("caio@caio.com");
			u.setPassword("caiao123");
			u.setStatus("Not-confirmed");
			u.setRole("User");

			userRepository.save(u);
		};
	}
}
