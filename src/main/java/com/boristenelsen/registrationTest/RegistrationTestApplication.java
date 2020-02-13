package com.boristenelsen.registrationTest;

import com.boristenelsen.registrationTest.properties.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@EnableConfigurationProperties(FileStorageProperties.class)
@Configuration
@SpringBootApplication
public class RegistrationTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(RegistrationTestApplication.class, args);
	}

}
