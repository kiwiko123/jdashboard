package com.kiwiko.jdashboard.springbootexample.service.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.kiwiko.jdashboard.springbootexample.service")
@EntityScan("com.kiwiko.jdashboard.springbootexample.service.data.entity")
public class SpringBootExampleServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootExampleServiceApplication.class, args);
	}
}
