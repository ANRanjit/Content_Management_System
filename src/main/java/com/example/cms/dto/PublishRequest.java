package com.example.cms.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PublishRequest {

	@NotNull(message = "Invalid Input")
	private String seoTitle;
	private String seoDescription;
	private String[] seoTopics;
	private ScheduleRequest schedule;
}
