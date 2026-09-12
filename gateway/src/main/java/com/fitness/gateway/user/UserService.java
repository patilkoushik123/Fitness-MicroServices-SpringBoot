package com.fitness.gateway.user;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
private final WebClient userSeviceWebClient;
public Mono<Boolean> validateUser(String userId) {
	log.info("Calling User Validation API for userID: {}",userId);

		return userSeviceWebClient.get().uri("/api/users/{userId}/validate", userId)
	 .retrieve()
	 .bodyToMono(Boolean.class)
	 .onErrorResume(WebClientResponseException.class, e ->{
		 if(e.getStatusCode() == HttpStatus.NOT_FOUND) {
			 return Mono.error(new RuntimeException("User Not Found: "+userId));
		 }else if(e.getStatusCode() == HttpStatus.BAD_REQUEST) {
			  return Mono.error(new RuntimeException("Invalid Request:" +userId));
		 }
         return Mono.error(new  RuntimeException("User validation failed: " + e.getMessage()));
	 });

}
public Mono<UserResponse> registerUser(RegisterRequest request) {
	// TODO Auto-generated method stub
	log.info("Calllin User Registration API for email: {}",request.getEmail());
	return userSeviceWebClient.post().uri("/api/users/register")
			.bodyValue(request)
			 .retrieve()
			 .bodyToMono(UserResponse.class)
			 .onErrorResume(WebClientResponseException.class, e ->{
				 if(e.getStatusCode() == HttpStatus.BAD_REQUEST) {
					 return Mono.error(new RuntimeException("Bad Request: "+e.getMessage()));
				 }else if(e.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
					  return Mono.error(new RuntimeException("Internal Server Error" +e.getMessage()));
				 }
		         return Mono.error(new  RuntimeException("User validation failed: " + e.getMessage()));
			 });
}
}
