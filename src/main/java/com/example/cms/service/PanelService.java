package com.example.cms.service;

import org.springframework.http.ResponseEntity;

import com.example.cms.entity.ContributionPanel;
import com.example.cms.utility.ResponseStructure;

public interface PanelService {

	ResponseEntity<ResponseStructure<ContributionPanel>> addContributor(int panelId, int userId);

	ResponseEntity<ResponseStructure<ContributionPanel>> removeContributor(int panelId, int userId);

	
}
