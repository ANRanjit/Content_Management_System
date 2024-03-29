package com.example.cms.dto;

import java.util.List;

import com.example.cms.entity.User;

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
public class BlogResponse {

	
	private int blogId;
	private String title;
	private String[] topics;
	private String about;
	private List<User> users;
}
