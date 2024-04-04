package com.example.cms.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BlogPostNotFoundByIdAndPostTypeByPublishedException  extends RuntimeException{

	private String message;
}
