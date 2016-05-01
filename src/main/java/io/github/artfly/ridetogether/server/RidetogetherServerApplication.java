package io.github.artfly.ridetogether.server;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.File;
import java.util.Arrays;

@SpringBootApplication
public class RidetogetherServerApplication {
	public static final String IMAGES = "images";

	@Bean
	CommandLineRunner init() {
		return (String[] args) -> new File(IMAGES).mkdir();
	}

	public static void main(String[] args) {
		SpringApplication.run(RidetogetherServerApplication.class, args);
	}
}
