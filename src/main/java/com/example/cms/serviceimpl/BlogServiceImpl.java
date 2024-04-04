package com.example.cms.serviceimpl;

import java.sql.Array;
import java.util.Arrays;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.cms.dto.BlogResponse;
import com.example.cms.entity.Blog;
import com.example.cms.entity.ContributionPanel;
import com.example.cms.entity.User;
import com.example.cms.exceptions.BlogAlreadyExistedByTitleException;
import com.example.cms.exceptions.BlogNotFoundByIdException;
import com.example.cms.exceptions.TopicsNotSpecifiedException;
import com.example.cms.exceptions.UserNotFoundException;
import com.example.cms.dto.BlogRequest;
import com.example.cms.repository.BlogRepository;
import com.example.cms.repository.PanelRepository;
import com.example.cms.repository.UserRepository;
import com.example.cms.service.BlogService;
import com.example.cms.utility.ResponseStructure;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class BlogServiceImpl implements BlogService {

	private BlogRepository blogRepository;
	private UserRepository userRepository;
	private ResponseStructure<BlogResponse> responseStructure;
	private PanelRepository panelRepository;
	@Override
    public ResponseEntity<ResponseStructure<BlogResponse>> createblog(BlogRequest blogRequest, int userId) {
//		String email = SecurityContextHolder.getContext().getAuthentication().getName();
//		return userRepository.findByUserEmail(email).map(user->
//		return userRepository.findById(userId).map(user->{
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		return userRepository.findByUserEmail(email).map(user->{
				if(blogRepository.existsByTitle(blogRequest.getTitle()))
					throw new BlogAlreadyExistedByTitleException("Failed to create blog");
				if(blogRequest.getTopics().length<1)
					throw new TopicsNotSpecifiedException("failed to create a blog");
				Blog blog = mapToBlog(blogRequest, new Blog());
				ContributionPanel panel=panelRepository.save(new ContributionPanel());
				blog.setContributionPanel(panel);
				blog.setUser(user);
				blog=blogRepository.save(blog);
				return ResponseEntity.ok(responseStructure
						.setStatusCode(HttpStatus.OK.value())
						.setMessage("Blog Created Successfully").setData(mapToBlogResponse(blog)));
				
		}).get();/*orElseThrow(()-> new UserNotFoundException("failed to creae blog"));*/

		}

	private BlogResponse mapToBlogResponse(Blog blog) {
		
		return BlogResponse.builder().blogId(blog.getBlogId())
				.title(blog.getTitle())
				.about(blog.getAbout())
				.topics(blog.getTopics())
				.user(blog.getUser()).build();
	}

	private Blog mapToBlog(BlogRequest blogRequest, Blog blog) {
		blog.setTitle(blogRequest.getTitle());
		blog.setTopics(blogRequest.getTopics());
		blog.setAbout(blogRequest.getAbout());
		return blog;
	}

	@Override
	public ResponseEntity<Boolean> isPresent(String title) {
		boolean a = blogRepository.existsByTitle(title);
		return ResponseEntity.ok(a);
	}

	@Override
	public ResponseEntity<ResponseStructure<BlogResponse>> findBlog(int blogId) {
		return blogRepository.findById(blogId).map(blog->
		ResponseEntity.ok(
				responseStructure.setStatusCode(HttpStatus.OK.value())
				.setMessage("Blog Found")
				.setData(mapToBlogResponse(blog))))
				.orElseThrow(()->new BlogNotFoundByIdException("blog not found"));
	}

	@Override
	public ResponseEntity<ResponseStructure<BlogResponse>> updateBlog(int blogId, BlogRequest blogRequest) {
		return blogRepository.findById(blogId).map(blog->{
		     if(blogRepository.existsByTitle(blogRequest.getTitle()))
			       throw new BlogAlreadyExistedByTitleException("Failed to create blog");
		     if(blogRequest.getTopics().length<1)
			        throw new TopicsNotSpecifiedException("failed to create a blog");
		     Blog uniqueblog = mapToBlog(blogRequest, new Blog());
		     blogRepository.save(uniqueblog);
		     return ResponseEntity.ok(
		    		 responseStructure.setStatusCode(HttpStatus.OK.value())
		    		 	.setMessage("Blog Updated Successfully ")
		    		 	.setData(mapToBlogResponse(uniqueblog)));
		     })
			.orElseThrow(()->new BlogNotFoundByIdException("Blog Not Found"));
	}
}
