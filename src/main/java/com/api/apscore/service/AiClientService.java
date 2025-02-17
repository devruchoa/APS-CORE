package com.api.apscore.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AiClientService {

    @Value("${openai.api.key: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}")
    private String openAiKey;

    @Value("${openai.api.url:https://api.openai.com/v1/chat/completions}")
    private String openAiUrl;

    public String generateWorkoutPlan(String prompt) {
        Map<String, Object> requestBody = Map.of(
                "model", "gpt-3.5-turbo",
                "messages", List.of(
                        Map.of("role", "system",
                                "content", "Você é um personal trainer especializado em musculação. " +
                                        "Gere planos de treino de forma clara e organizada."),
                        Map.of("role", "user",
                                "content", prompt)
                ),
                "max_tokens", 512,
                "temperature", 0.7
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(openAiKey);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> response = restTemplate.exchange(
                openAiUrl,
                HttpMethod.POST,
                entity,
                Map.class
        );

        Map<?, ?> responseBody = response.getBody();
        List<?> choices = (List<?>) responseBody.get("choices");
        if (choices != null && !choices.isEmpty()) {
            Map<?, ?> choice = (Map<?, ?>) choices.get(0);
            Map<?, ?> message = (Map<?, ?>) choice.get("message");
            if (message != null && message.containsKey("content")) {
                return message.get("content").toString();
            }
        }

        return "Não foi possível gerar uma rotina de treino.";
    }
}
