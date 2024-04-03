package com.example.cms.service;

import org.springframework.http.ResponseEntity;

import com.example.cms.dto.BlogPostRequest;
import com.example.cms.dto.BlogPostResponse;
import com.example.cms.utility.ResponseStructure;

public interface BlogPostService {

	ResponseEntity<ResponseStructure<BlogPostResponse>> createBlogPost(BlogPostRequest blogPostRequest, int blogId);

    ResponseEntity<ResponseStructure<BlogPostResponse>> updateBlogPost(BlogPostRequest blogPostRequest,
			int blogPostId);

	ResponseEntity<ResponseStructure<BlogPostResponse>> deleteBlogPost(int postId);

	ResponseEntity<ResponseStructure<BlogPostResponse>> updateBlogPostType(int postId);

}
