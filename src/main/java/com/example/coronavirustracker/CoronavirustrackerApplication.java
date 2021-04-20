package com.example.coronavirustracker;

import com.example.coronavirustracker.Repository.UserRepository;
import com.example.coronavirustracker.config.SwaggerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
@Import(SwaggerConfig.class)
public class CoronavirustrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoronavirustrackerApplication.class, args);
	}

}
