package io.github.artfly.ridetogether.server;


import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

import java.io.File;

@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class RidetogetherServerApplication extends SpringBootServletInitializer {
    public static final String IMAGES = "images";

    public static void main(String[] args) {
        SpringApplication.run(RidetogetherServerApplication.class, args);
    }

    @Bean
    CommandLineRunner init() {
        return (String[] args) -> new File(IMAGES).mkdir();
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(RidetogetherServerApplication.class);
    }


    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
