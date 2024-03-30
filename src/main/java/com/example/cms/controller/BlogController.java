package com.example.cms.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.cms.dto.BlogResponse;
import com.example.cms.dto.BlogRequest;
import com.example.cms.entity.Blog;
import com.example.cms.service.BlogService;
import com.example.cms.utility.ResponseStructure;

import lombok.AllArgsConstructor;
@AllArgsConstructor
@RestController
public class BlogController {

	private BlogService blogService;
	
	@PostMapping("/user/{userId}/blogs")
	public ResponseEntity<ResponseStructure<BlogResponse>> createBlog(@RequestBody BlogRequest blogRequest, @PathVariable int userId )
	{
		return blogService.createblog(blogRequest,userId);
	}
	
	@GetMapping("/title/{title}/blogs")
	public ResponseEntity<Boolean> isPresent(@PathVariable String  title)
	{
		return blogService.isPresent(title);
	}
	
	@GetMapping("/blogs/{blogId}")
	public ResponseEntity<ResponseStructure<BlogResponse>> findBlog(@PathVariable int blogId)
	{
		return blogService.findBlog(blogId);
	}
	
	@PutMapping("/blogs/{blogId}")
	public ResponseEntity<ResponseStructure<BlogResponse>> updateBlog(@PathVariable int blogId,@RequestBody BlogRequest blogRequest)
	{
		return blogService.updateBlog(blogId,blogRequest);
	}
}
