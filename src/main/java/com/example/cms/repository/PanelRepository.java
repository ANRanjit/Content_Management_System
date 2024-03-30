package com.example.cms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.cms.entity.ContributionPanel;
import com.example.cms.entity.User;

public interface PanelRepository extends JpaRepository<ContributionPanel, Integer>{


	boolean existsByContributers(User contributor);

}
