package com.fitness.userservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fitness.userservice.dto.RegisterRequest;
import com.fitness.userservice.dto.UserResponse;
import com.fitness.userservice.model.User;
import com.fitness.userservice.repository.UserRepository;


@Service
public class UserService {
	       @Autowired
          private UserRepository repository;


	public UserResponse register(RegisterRequest request) {
		if(repository.existsByEmail(request.getEmail())) {
			User existingUser = repository.findByEmail(request.getEmail());
			UserResponse userResponse = new UserResponse();
			userResponse.setId(existingUser.getId());
			userResponse.setKeycloakId(existingUser.getKeycloakId());
			userResponse.setPassword(existingUser.getPassword());
			userResponse.setEmail(existingUser.getEmail());
			userResponse.setFirstName(existingUser.getFirstName());
			userResponse.setLastName(existingUser.getLastName());
			userResponse.setCreatedAt(existingUser.getCreatedAt());
			userResponse.setUpdatedAt(existingUser.getUpdatedAt());
			return userResponse;
			
			
		}
		User user = new User();
		user.setEmail(request.getEmail());
		user.setPassword(request.getPassword());
		user.setKeycloakId(request.getKeycloakId());
	     user.setFirstName(request.getFirstName());
	     user.setLastName(request.getLastName());
		User savedUser= repository.save(user);
		UserResponse userResponse = new UserResponse();
		userResponse.setId(savedUser.getId());
		userResponse.setKeycloakId(savedUser.getKeycloakId());
        userResponse.setPassword(savedUser.getPassword());
		userResponse.setEmail(savedUser.getEmail());
		userResponse.setFirstName(savedUser.getFirstName());
		userResponse.setLastName(savedUser.getLastName());
		userResponse.setCreatedAt(savedUser.getCreatedAt());
		userResponse.setUpdatedAt(savedUser.getUpdatedAt());
		return userResponse;


		
	}


	public UserResponse getUserProfile(String userId) {
		// TODO Auto-generated method stub
	    User user=repository.findById(userId)
	    	.orElseThrow(() -> new RuntimeException("User Not Found"));
	   UserResponse userResponse = new UserResponse();
	   userResponse.setId(user.getId());
	   userResponse.setPassword(user.getPassword());
	   userResponse.setEmail(user.getEmail());
	   userResponse.setFirstName(user.getFirstName());
	   userResponse.setLastName(user.getLastName());
	   userResponse.setCreatedAt(user.getCreatedAt());
	   userResponse.setUpdatedAt(user.getUpdatedAt());
        return userResponse;
	}


	public Boolean existsByuserId(String userId) {
		// TODO Auto-generated method stub
		return repository.existsByKeycloakId(userId);
	}
	

}

