package com.example.cms.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.cms.dto.UserRequest;
import com.example.cms.dto.UserResponse;
import com.example.cms.entity.User;
import com.example.cms.exceptions.UserAlreadyExistByEmailException;
import com.example.cms.exceptions.UserNotFoundException;
import com.example.cms.repository.UserRepository;
import com.example.cms.service.UserService;
import com.example.cms.utility.ResponseStructure;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{

	UserRepository userRepository;

	private ResponseStructure<UserResponse> responseStructure;

	private ResponseStructure<List<User>> responseStructureList;
	
    private PasswordEncoder passwordEncoder;

	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> saveUser(UserRequest userRequest) {
		if(userRepository.existsByuserEmail(userRequest.getUserEmail()))
			throw new UserAlreadyExistByEmailException("Failed to regeister user");
		User user = userRepository.save(mapToUser(userRequest,new User()));
		return ResponseEntity.ok(responseStructure.setStatusCode(HttpStatus.OK.value()).setMessage("User Registered Successfully").setData(mapToUserResponse(user)));
	}
	
	private UserResponse mapToUserResponse(User user) {
		return UserResponse.builder().userId(user.getUserId()).userEmail(user.getUserEmail()).userName(user.getUserName()).createdAt(user.getCreatedAt()).latsModifiedAt(user.getLastModifiedAt()).build();
	}

	public User mapToUser(UserRequest userRequest,User user) {
		user.setUserName(userRequest.getUserName());
		user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
		user.setUserEmail(userRequest.getUserEmail());
		return user;
	}

	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> findUser(int userId) {
		return userRepository.findById(userId).map(user->ResponseEntity.ok(
				responseStructure.setStatusCode(HttpStatus.OK.value())
				.setMessage("User Found").setData(mapToUserResponse(user))))
				.orElseThrow(()-> new UserNotFoundException("user not found"));
	}
	public ResponseEntity<ResponseStructure<UserResponse>> deleteByUserId(int userId) {
		return userRepository.findById(userId).map(user ->{
			user.setDeleted(true);
			User uniqueuser = userRepository.save(user);
			return ResponseEntity.ok(
				responseStructure.setStatusCode(HttpStatus.OK.value())
				.setMessage("User Found and will be deleted in 30 days")
				.setData(mapToUserResponse(uniqueuser)));
		})
				.orElseThrow(()-> new UserNotFoundException("User does not exist"));
		
	}
	
}
