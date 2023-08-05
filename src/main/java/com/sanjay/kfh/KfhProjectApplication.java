package com.sanjay.kfh;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.convention.NameTokenizers;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class KfhProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(KfhProjectApplication.class, args);
	}

	@Bean
	public PasswordEncoder passwordEncoder(){
		return  new BCryptPasswordEncoder(8);
	}

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

}
