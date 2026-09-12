package com.fitness.aiservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fitness.aiservice.model.Recommendation;
import com.fitness.aiservice.repository.RecommendationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecommendationService {
private final RecommendationRepository recommendationRepository;

public List<Recommendation> getUserRecommendation(String userId) {
	// TODO Auto-generated method stub
	
	return recommendationRepository.findByUserId(userId);
}

public Recommendation getActivityRecommendation(String activityId) {
	// TODO Auto-generated method stub
	return recommendationRepository.findByActivityId(activityId);
			//.orElseThrow(() -> new RuntimeException("No recommendation found"));


}
}
