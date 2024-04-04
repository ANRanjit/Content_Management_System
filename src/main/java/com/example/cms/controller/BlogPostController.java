package com.example.cms.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.cms.dto.BlogPostRequest;
import com.example.cms.dto.BlogPostResponse;
import com.example.cms.service.BlogPostService;
import com.example.cms.utility.ResponseStructure;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class BlogPostController {
	private BlogPostService blogPostService;
	
	@PostMapping("/blog/{blogId}/blogspost")
	public ResponseEntity<ResponseStructure<BlogPostResponse>> createBlog(@RequestBody BlogPostRequest blogPostRequest,@PathVariable("blogId") int blogId)
	{
		return blogPostService.createBlogPost(blogPostRequest,blogId);
	}
	
	@PutMapping("/blog-posts/{postId}")
	public ResponseEntity<ResponseStructure<BlogPostResponse>> updateBlogPost(@RequestBody BlogPostRequest blogPostRequest,@PathVariable int postId)
	{
		return blogPostService.updateBlogPost(blogPostRequest, postId);
	}
	
	@DeleteMapping("/blog-posts/{postId}")
	public ResponseEntity<ResponseStructure<BlogPostResponse>> deleteBlogPost(@PathVariable int postId)
	{
		return blogPostService.deleteBlogPost(postId);
	}
	
	@PutMapping("/blog-post/{postId}")
	public ResponseEntity<ResponseStructure<BlogPostResponse>> updatePostType(@PathVariable int postId)
	{
		return blogPostService.updateBlogPostType(postId);
	}
	
	@GetMapping("/blog-posts/{postId}")
	public ResponseEntity<ResponseStructure<BlogPostResponse>> getBlogPostById(@PathVariable int postId)
	{
		return blogPostService.getBlogPost(postId);
	}
	@GetMapping("/blog/{postId}/published")
	public ResponseEntity<ResponseStructure<BlogPostResponse>> getBlogPostByPublished(@PathVariable int postId){
		return blogPostService.getBlogPostByPublished(postId);
	}
}
