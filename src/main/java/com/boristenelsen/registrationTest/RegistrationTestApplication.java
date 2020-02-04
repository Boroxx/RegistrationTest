package com.boristenelsen.registrationTest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@EnableAutoConfiguration
@ComponentScan
@Configuration
public class RegistrationTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(RegistrationTestApplication.class, args);
	}

}
