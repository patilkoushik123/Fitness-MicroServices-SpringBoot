package com.fitness.activityservice.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserValidationService {
private final WebClient userSeviceWebClient;
public boolean validateUser(String userId) {
	try {
		return userSeviceWebClient.get().uri("/api/users/{userId}/validate", userId)
	 .retrieve()
	 .bodyToMono(Boolean.class)
	 .block();
	} catch (WebClientResponseException e) {
		 if(e.getStatusCode() == HttpStatus.NOT_FOUND) {
			 throw new RuntimeException("User Not Found: "+userId);
		 }else if(e.getStatusCode() == HttpStatus.BAD_REQUEST) {
			 throw new RuntimeException("Invalid Request:" +userId);
		 }
         throw new RuntimeException("User validation failed: " + e.getMessage());

	}
}
}
