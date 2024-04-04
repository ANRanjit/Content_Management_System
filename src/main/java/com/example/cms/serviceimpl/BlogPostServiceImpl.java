package com.example.cms.serviceimpl;

import java.util.Arrays;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.cms.dto.BlogPostRequest;
import com.example.cms.dto.BlogPostResponse;
import com.example.cms.dto.PublishResponse;
import com.example.cms.entity.Blog;
import com.example.cms.entity.BlogPost;
import com.example.cms.entity.ContributionPanel;
import com.example.cms.enums.PostType;
import com.example.cms.exceptions.BlogNotFoundByIdException;
import com.example.cms.exceptions.BlogPostAlreadyInDraftException;
import com.example.cms.exceptions.BlogPostNotFoundByIdAndPostTypeByPublishedException;
import com.example.cms.exceptions.BlogPostNotFoundByIdException;
import com.example.cms.exceptions.IllegalAccessRequestException;
import com.example.cms.exceptions.IllegalAccessRequestForUpdateException;
import com.example.cms.exceptions.UserNotFoundException;
import com.example.cms.repository.BlogPostRepository;
import com.example.cms.repository.BlogRepository;
import com.example.cms.repository.PanelRepository;
import com.example.cms.repository.UserRepository;
import com.example.cms.service.BlogPostService;
import com.example.cms.utility.ResponseStructure;

import lombok.AllArgsConstructor;
@Service
@AllArgsConstructor
public class BlogPostServiceImpl implements BlogPostService{

	BlogRepository blogRepository;
	BlogPostRepository blogPostRepository;
	ResponseStructure<BlogPostResponse> responseStructure;
	UserRepository userRepository;
	PanelRepository panelRepository;
	PublishServiceImpl publishServiceImpl;
	
	private BlogPost mapToBlogPostEntity(BlogPostRequest blogPostRequest, BlogPost blogPost) {
		blogPost.setTitle(blogPostRequest.getTitle());
		blogPost.setSubTitle(blogPostRequest.getSubTitle());
		blogPost.setSummary(blogPostRequest.getSummary());
		return blogPost;
	}
	private BlogPostResponse  mapToBlogPostResponse(BlogPost blogPost)
	{
		return BlogPostResponse.builder()
				.title(blogPost.getTitle())
				.subTitle(blogPost.getSubTitle())
				.postType(blogPost.getPostType())
				.summary(blogPost.getSummary())
				.publishResponse(publishServiceImpl.mapToPublishResponse(blogPost.getPublish()))
				.build();
	}

	@Override
	public ResponseEntity<ResponseStructure<BlogPostResponse>> createBlogPost(BlogPostRequest blogPostRequest,
			int blogId) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
     	return userRepository.findByUserEmail(email).map(user->{
		return blogRepository.findById(blogId).map(blog->{
			if(!blog.getUser().getUserEmail().equals(email)&&
					!panelRepository.existsByPanelIdAndContributers(blog.getContributionPanel().getPanelId(),user))
				throw new IllegalAccessRequestException("Unauthorized User");
			BlogPost blogPost =mapToBlogPostEntity(blogPostRequest,new BlogPost());
			blogPost.setPostType(PostType.DRAFT);
			blogPost.setBlog(blog);
			blogPostRepository.save(blogPost);
			return ResponseEntity.ok(responseStructure.setStatusCode(HttpStatus.OK.value())
					.setMessage("Blog Post Created Successfully")
					.setData(mapToBlogPostResponse(blogPost)));
		})
				.orElseThrow(()-> new BlogNotFoundByIdException("Invalid Blog Id"));
     	}).get();
	}

	
	@Override
	public ResponseEntity<ResponseStructure<BlogPostResponse>> updateBlogPost(BlogPostRequest blogPostRequest,
			int blogPostId) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		return userRepository.findByUserEmail(email).map(user->{
		   return blogPostRepository.findById(blogPostId).map(blogPost->{
			return blogRepository.findById(blogPost.getBlog().getBlogId()).map(blog->{
				if(!blog.getUser().getUserEmail().equals(email)&&
						!panelRepository.existsByPanelIdAndContributers(blog.getContributionPanel().getPanelId(),user))
					throw new IllegalAccessRequestException("Unautherized User");
			BlogPost exBlogPost = mapToBlogPostEntity(blogPostRequest,blogPost);
			blogPostRepository.save(exBlogPost);
			return ResponseEntity.ok(
					responseStructure.setStatusCode(HttpStatus.OK.value())
					.setMessage("BLogPost Updated")
					.setData(mapToBlogPostResponse(exBlogPost)));
			}).orElseThrow(()->new BlogNotFoundByIdException("Blog not found"));
		}).orElseThrow(()-> new BlogPostNotFoundByIdException("Blog Post Not Found"));
	}).get();
	}

	@Override
	public ResponseEntity<ResponseStructure<BlogPostResponse>> deleteBlogPost(int postId) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		return userRepository.findByUserEmail(email).map(user->{
			return blogPostRepository.findById(postId).map(blogPost->{
					if(!blogPost.getBlog().getUser().getUserEmail().equals(email) && 
							!blogPost.getCreatedBy().equals(email))
						 throw new IllegalAccessRequestException("UnAutherised User");
					blogPostRepository.delete(blogPost);
					return ResponseEntity.ok(responseStructure.setStatusCode(HttpStatus.OK.value())
							.setMessage("BlogPost Deleted Successfully")
							.setData(mapToBlogPostResponse(blogPost)));
			}).orElseThrow(()-> new BlogPostNotFoundByIdException("Unable to delete"));
		}).get();
	}

	@Override
	public ResponseEntity<ResponseStructure<BlogPostResponse>> updateBlogPostType(int postId) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		return userRepository.findByUserEmail(email).map(user->{
			return blogPostRepository.findById(postId).map(blogPost->{
				if(!blogPost.getBlog().getUser().getUserEmail().equals(email) && 
						!blogPost.getCreatedBy().equals(email))
					 throw new IllegalAccessRequestException("UnAutherised User");
				if(blogPost.getPostType()==PostType.DRAFT)
					throw new BlogPostAlreadyInDraftException("Can't UnPublish");
				blogPost.setPostType(PostType.DRAFT);
				blogPostRepository.save(blogPost);
				return ResponseEntity.ok(
						responseStructure.setStatusCode(HttpStatus.OK.value())
						.setMessage("BlogPost Unpublished Successfully")
						.setData(mapToBlogPostResponse(blogPost)));
			}).orElseThrow(()->new BlogPostNotFoundByIdException("Can't Unpublish"));
	}).get();
	}

	@Override
	public ResponseEntity<ResponseStructure<BlogPostResponse>> getBlogPost(int postId) {
		
		return blogPostRepository.findById(postId).map(blogPost->{
			return ResponseEntity.ok(responseStructure.setStatusCode(HttpStatus.OK.value())
					.setMessage("Post Found")
					.setData(mapToBlogPostResponse(blogPost)));
		}).orElseThrow(()->new BlogPostNotFoundByIdException("Not found"));
	}

	@Override
	public ResponseEntity<ResponseStructure<BlogPostResponse>> getBlogPostByPublished(int postId) {
		return blogPostRepository.findByPostIdAndPostType(postId, PostType.PUBLISHED).map(post->
		ResponseEntity.ok(responseStructure.setStatusCode(HttpStatus.OK.value())
				.setMessage("Post Found")
				.setData(mapToBlogPostResponse(post)))
		).orElseThrow(()->new BlogPostNotFoundByIdAndPostTypeByPublishedException("Not found"));
	}
}
