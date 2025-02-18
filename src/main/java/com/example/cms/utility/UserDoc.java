package com.example.cms.utility;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
@OpenAPIDefinition
public class UserDoc {

	
	Contact contact() {
		return new Contact().name("Rohan S H")
				.url("xyz.in")
				.email("rohansh2851@gmail.com");
	}
	@Bean
	Info info()
	{
		return new Info().title("Content Management System")
				.description("RESTful API with basic crud operation")
				.version("v1");
	}

	@Bean
	OpenAPI openApi()
	{
		return new OpenAPI().info(info().contact(contact()));
	}
}


