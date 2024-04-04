package com.example.cms.service;

import org.springframework.http.ResponseEntity;

import com.example.cms.dto.PublishRequest;
import com.example.cms.dto.PublishResponse;
import com.example.cms.utility.ResponseStructure;

public interface PublishService {

	ResponseEntity<ResponseStructure<PublishResponse>> publishBlogPost(PublishRequest publishRequest,
			int postId);

}
