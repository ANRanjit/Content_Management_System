package com.example.cms.serviceimpl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.cms.entity.ContributionPanel;
import com.example.cms.exceptions.ContributersAlreadyPresentException;
import com.example.cms.exceptions.ContributersNotPresentException;
import com.example.cms.exceptions.IllegalAccessRequestException;
import com.example.cms.exceptions.PanelNotFoundByIdException;
import com.example.cms.exceptions.UserNotFoundException;
import com.example.cms.repository.BlogRepository;
import com.example.cms.repository.PanelRepository;
import com.example.cms.repository.UserRepository;
import com.example.cms.service.PanelService;
import com.example.cms.utility.ResponseStructure;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class PanleServiceImpl implements PanelService {


	private UserRepository userRepository;
	private BlogRepository blogRepository;
	private PanelRepository panelRepository;
	private ResponseStructure<ContributionPanel> responseStructure;
	@Override
	public ResponseEntity<ResponseStructure<ContributionPanel>> addContributor(int panelId, int userId) {

		String username = SecurityContextHolder.getContext().getAuthentication().getName();

		return userRepository.findByUserEmail(username).map(owner -> {
			return panelRepository.findById(panelId).map(panel -> {
				if (!blogRepository.existsByUserAndContributionPanel(owner, panel))
					throw new IllegalAccessRequestException("illegal accept request");
				 return userRepository.findById(userId).map(contributor -> {
					 if(panelRepository.existsByContributers(contributor))
						 throw new ContributersAlreadyPresentException("Unable to add");
					panel.getContributers().add(contributor);
					panelRepository.save(panel);
					 return ResponseEntity.ok(responseStructure.setData(panel).setMessage("user Added Successfully")
							.setStatusCode(HttpStatus.OK.value()));
				}).orElseThrow(() -> new UserNotFoundException("User not found"));
			}).orElseThrow(() -> new PanelNotFoundByIdException("Panel not found"));
		}).get();
	}
	@Override
	public ResponseEntity<ResponseStructure<ContributionPanel>> removeContributor(int panelId, int userId) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();

		return userRepository.findByUserEmail(username).map(owner -> {
			return panelRepository.findById(panelId).map(panel -> {
				if (!blogRepository.existsByUserAndContributionPanel(owner, panel))
					throw new IllegalAccessRequestException("Illegal accept request");
				 return userRepository.findById(userId).map(contributor -> {
					 if(!panelRepository.existsByContributers(contributor))
						 throw new ContributersNotPresentException("Unable to remove");
					panel.getContributers().remove(contributor);
					panelRepository.save(panel);
					 return ResponseEntity.ok(responseStructure.setData(panel).setMessage("user Deleted success")
							.setStatusCode(HttpStatus.OK.value()));
				}).orElseThrow(() -> new UserNotFoundException("User Not Found"));
			}).orElseThrow(() -> new PanelNotFoundByIdException("Panel Not Found"));
		}).get();
	}
}
