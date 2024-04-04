package com.example.cms.utility;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.cms.entity.BlogPost;
import com.example.cms.enums.PostType;
import com.example.cms.repository.BlogPostRepository;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ScheduledJob {

	BlogPostRepository blogPostRepository;
	
	@Scheduled(fixedDelay = 60*1000l)
	public void publishScheduledBlogPosts(){
	List<BlogPost> posts = blogPostRepository.findAllByPublishScheduleDateTimeLessThanEqualAndPostType(LocalDateTime.now(),PostType.SCHEDULED)
				.stream().map(post-> { 
					post.setPostType(PostType.PUBLISHED);
					return post;
				}).collect(Collectors.toList());
		blogPostRepository.saveAll(posts);
	}
	
}
