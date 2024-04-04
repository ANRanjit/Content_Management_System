package com.example.cms.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Publish {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int publishId;
	@NotNull(message = "Invalid Input")
	private String seoTitle;
	private String seoDescription;
	private String[] seoTopics;
	@OneToOne(mappedBy = "publish")
	private BlogPost blogPost;
	@OneToOne
	private Schedule schedule;
}
