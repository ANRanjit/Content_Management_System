package com.example.cms.dto;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import com.example.cms.entity.Blog;
import com.example.cms.entity.Publish;
import com.example.cms.enums.PostType;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BlogPostResponse {

	private int postId;
	private String title;
	private String subTitle;
	private String summary;
	private PostType postType;
	private String createdBy;
	private LocalDateTime createdAt;
	private LocalDateTime lastModifiedAt;
	private String lastModeifiedBy;
	private Blog blog;
	private Publish publish;
}
