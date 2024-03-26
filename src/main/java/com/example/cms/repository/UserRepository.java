package com.example.cms.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.cms.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

	boolean existsByuserEmail(String userEmail);
	Optional<User> findByUserEmail(String userEmail);

}
