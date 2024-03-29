package com.example.cms.service;

import org.springframework.http.ResponseEntity;

import com.example.cms.dto.BlogResponse;
import com.example.cms.dto.BlogRequest;
import com.example.cms.utility.ResponseStructure;

public interface BlogService {

	ResponseEntity<ResponseStructure<BlogResponse>> createblog(BlogRequest blogRequest, int userId);

	ResponseEntity<Boolean> isPresent(String title);

	ResponseEntity<ResponseStructure<BlogResponse>> findBlog(int blogId);

	ResponseEntity<ResponseStructure<BlogResponse>> updateBlog(int blogId, BlogRequest blogRequest);

}
