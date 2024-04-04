package com.example.cms.dto;

import com.example.cms.enums.PostType;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BlogPostRequest {

	@NotNull(message = "Invalid Input")
	private String title;
	private String subTitle;
	@Size(min = 500, max = 5000, message = "Charecter should be more tha 500")
	private String summary;
}
