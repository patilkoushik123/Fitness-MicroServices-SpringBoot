package com.fitness.aiservice.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class GeminiService {

    @Value("${groq.api.url}")
    private String groqApiUrl;

    @Value("${groq.api.model}")
    private String groqModel;

    // Comes from Environment Variables
    @Value("${GROQ_API_KEY}")
    private String groqApiKey;

    private final WebClient.Builder webClientBuilder;

    public String getAnswer(String prompt) {
        WebClient webClient = webClientBuilder.build();

        Map<String, Object> requestBody = Map.of(
                "model", groqModel,
                "messages", List.of(
                        Map.of(
                                "role", "user",
                                "content", prompt
                        )
                )
        );

        try {
            Map<?, ?> response = webClient.post()
                    .uri(groqApiUrl)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + groqApiKey)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            if (response == null) {
                return "AI returned null response";
            }

            List<Map<String, Object>> choices =
                    (List<Map<String, Object>>) response.get("choices");

            if (choices == null || choices.isEmpty()) {
                return "AI returned empty choices";
            }

            Map<String, Object> message =
                    (Map<String, Object>) choices.get(0).get("message");

            return message.get("content").toString();

        } catch (WebClientResponseException e) {
            log.error("Groq API error: {}", e.getResponseBodyAsString(), e);
            return "Groq API Error";

        } catch (Exception e) {
            log.error("Unexpected Groq error", e);
            return "Unexpected AI error";
        }
    }
}
