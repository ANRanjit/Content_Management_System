package com.example.cms.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.cms.entity.ContributionPanel;
import com.example.cms.entity.User;
import com.example.cms.service.PanelService;
import com.example.cms.utility.ResponseStructure;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class PanelController {

	private PanelService panelService;
	
	@PostMapping("/user/{userId}/contribution-panel/{panelId}")
	public ResponseEntity<ResponseStructure<ContributionPanel>> addContributor(@PathVariable int panelId,@PathVariable int userId)
	{
	return panelService.addContributor(panelId,userId);
	}
	
	@DeleteMapping("/user/{userId}/contribution-panel/{panelId}")
	public ResponseEntity<ResponseStructure<ContributionPanel>> removeContributor(@PathVariable int panelId,@PathVariable int userId)
	{
		return panelService.removeContributor(panelId,userId);
	}
}
