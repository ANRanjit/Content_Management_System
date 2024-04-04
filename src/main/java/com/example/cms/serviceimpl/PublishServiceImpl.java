package com.example.cms.serviceimpl;


import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.cms.dto.PublishRequest;
import com.example.cms.dto.PublishResponse;
import com.example.cms.dto.ScheduleRequest;
import com.example.cms.entity.BlogPost;
import com.example.cms.entity.Publish;
import com.example.cms.entity.Schedule;
import com.example.cms.enums.PostType;
import com.example.cms.exceptions.BlogPostNotFoundByIdException;
import com.example.cms.exceptions.IllegalAccessRequestException;
import com.example.cms.exceptions.ScheduleTimeNotValidException;
import com.example.cms.repository.BlogPostRepository;
import com.example.cms.repository.PublishRepository;
import com.example.cms.repository.ScheduleRepository;
import com.example.cms.service.PublishService;
import com.example.cms.utility.ResponseStructure;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PublishServiceImpl implements PublishService{
	
	private ResponseStructure<PublishResponse> responseStructure;
	private BlogPostRepository blogPostRepository;
	private PublishRepository publishRepository;
	private ScheduleRepository scheduleRepository;

	private Publish mapToPublishEntity(PublishRequest publishRequest,Publish publish)
	{
		publish.setSeoTitle(publishRequest.getSeoTitle());
		publish.setSeoDescription(publishRequest.getSeoDescription());
		publish.setSeoTopics(publishRequest.getSeoTopics());
		publish.setSchedule(mapToSchedule(publishRequest.getSchedule(), new Schedule()));
		return publish;
	}
	
	 private Schedule mapToSchedule(ScheduleRequest scheduleRequest ,Schedule schedule) {
		
		schedule.setDateTime(scheduleRequest.getDateTime());
		return schedule;
	}

	PublishResponse mapToPublishResponse(Publish publish)
	{
		 if(publish==null)
			 return PublishResponse.builder().build();
		return PublishResponse.builder().publishId(publish.getPublishId())
				.seoTitle(publish.getSeoTitle())
				.seoDescription(publish.getSeoDescription())
				.seoTopics(publish.getSeoTopics())
				.schedule(publish.getSchedule())
				.build();
	}
	
	@Override
	public ResponseEntity<ResponseStructure<PublishResponse>> publishBlogPost(PublishRequest publishRequest,
			int postId) {	
		String email=SecurityContextHolder.getContext().getAuthentication().getName();
		return blogPostRepository.findById(postId).map(blogPost->{
			if(!blogPost.getBlog().getUser().getUserEmail().equals(email)
					&& !blogPost.getCreatedBy().equals(email))
				throw new IllegalAccessRequestException("Unautherized User");
		    Publish publish=null;
		    if(blogPost.getPublish()!=null)
		    {
		    	publish=mapToPublishEntity(publishRequest, blogPost.getPublish());
		    }else {
		    	publish=mapToPublishEntity(publishRequest, new Publish());
		    }
		    if(publishRequest.getSchedule()!=null)
		    {
		    	if(publishRequest.getSchedule().getDateTime().isBefore(LocalDateTime.now()))
		    	   throw new ScheduleTimeNotValidException("Unable to Publish");
		    	if(publish.getSchedule()==null)
		    	{
		    	publish.setSchedule(scheduleRepository.save(mapToSchedule(publishRequest.getSchedule(), new Schedule())));
		    	blogPost.setPostType(PostType.SCHEDULED);
		    	}else {
		    		scheduleRepository.save(mapToSchedule(publishRequest.getSchedule(), publish.getSchedule()));
		    		blogPost.setPostType(PostType.SCHEDULED);
		    	}
		    } else {
		    	blogPost.setPostType(PostType.PUBLISHED);
		    }
		    publish.setBlogPost(blogPost);
		    blogPostRepository.save(blogPost);
		    publishRepository.save(publish);
		    return ResponseEntity.ok(
		    		responseStructure.setStatusCode(HttpStatus.OK.value())
		    		.setMessage("Blog Published")
		    		.setData(mapToPublishResponse(publish)));
		}).orElseThrow(()->new BlogPostNotFoundByIdException("Unable to publish "));
	}

}
