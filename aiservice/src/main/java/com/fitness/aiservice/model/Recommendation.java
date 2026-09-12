package com.fitness.aiservice.model;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;


@Document(collection="recommendations")

@Data
@Builder
public class Recommendation {
private String id;
private String activityId;
private String userId;
private String activityType;
private String recommendation;
private List<String> improvements;
private List<String> suggestions;
private List<String> safety;
private LocalDateTime createdAt;

	
}



