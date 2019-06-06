package com.frantishex.loyaltyprogramme;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = { "com.frantishex.loyaltyprogramme" })
@EntityScan("com.frantishex.loyaltyprogramme.models")
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}