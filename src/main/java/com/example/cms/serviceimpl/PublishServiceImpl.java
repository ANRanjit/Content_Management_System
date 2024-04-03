package com.example.cms.serviceimpl;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.cms.dto.PublishRequest;
import com.example.cms.dto.PublishResponse;
import com.example.cms.entity.BlogPost;
import com.example.cms.entity.Publish;
import com.example.cms.enums.PostType;
import com.example.cms.exceptions.IllegalAccessRequestException;
import com.example.cms.repository.BlogPostRepository;
import com.example.cms.service.PublishService;
import com.example.cms.utility.ResponseStructure;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PublishServiceImpl implements PublishService{
	
	private ResponseStructure<PublishResponse> responseStructure;
	private BlogPostRepository blogPostRepository;


	private Publish mapToPublishEntity(PublishRequest publishRequest,Publish publish)
	{
		publish.setSeoTitle(publishRequest.getSeoTitle());
		publish.setSeoDescription(publishRequest.getSeoDescription());
		publish.setSeoTopics(publishRequest.getSeoTopics());
		return publish;
	}
	
	private PublishResponse mapToPublishResponse(Publish publish)
	{
		return PublishResponse.builder().publishId(publish.getPublishId())
				.seoTitle(publish.getSeoTitle())
				.seoDescription(publish.getSeoDescription())
				.seoTopics(publish.getSeoTopics())
				.blogPost(publish.getBlogPost()).build();
	}
	
	@Override
	public ResponseEntity<ResponseStructure<PublishResponse>> publishBlogPost(PublishRequest publishRequest,
			int postId) {
		
		String email=SecurityContextHolder.getContext().getAuthentication().getName();
		return blogPostRepository.findById(postId).map(blogPost->{
			if(!blogPost.getBlog().getUser().getUserEmail().equals(email)
					&& !blogPost.getCreatedBy().equals(email))
				throw new IllegalAccessRequestException("Unautherized User");
			if(blogPost.getPublish()==null) {
				blogPost.setPostType(PostType.PUBLISHED);
				BlogPost post = blogPostRepository.save(blogPost);
				Publish publish = mapToPublishEntity(publishRequest, new Publish());
				publish.setBlogPost(post);
				return ResponseEntity.ok(responseStructure.setStatusCode(HttpStatus.OK.value()).setMessage("Published").setData(mapToPublishResponse(publish)));
			}
			else
			{
				blogPost.setPostType(PostType.PUBLISHED);
				BlogPost post = blogPostRepository.save(blogPost);
				Publish publish = post.getPublish();
				return ResponseEntity.ok(responseStructure.setData(mapToPublishResponse(publish)).setMessage("Published"));
			}
		}).get();
	}

}
