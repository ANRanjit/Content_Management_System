package com.example.cms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class ContentManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(ContentManagementSystemApplication.class, args);
	}

}
