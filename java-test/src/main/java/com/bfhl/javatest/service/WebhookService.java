package com.bfhl.javatest.service;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.bfhl.javatest.model.WebhookRequest;
import com.bfhl.javatest.model.WebhookResponse;
import com.bfhl.javatest.model.FinalQueryRequest;

@Service
public class WebhookService {

    public WebhookResponse generateWebhook(WebhookRequest request) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<WebhookRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<WebhookResponse> response = restTemplate.exchange(
                url, HttpMethod.POST, entity, WebhookResponse.class
        );

        return response.getBody();
    }

    public void submitFinalQuery(String accessToken, String webhookUrl, String finalQuery) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken);

        FinalQueryRequest body = new FinalQueryRequest(finalQuery);
        HttpEntity<FinalQueryRequest> entity = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    webhookUrl,
                    HttpMethod.POST,
                    entity,
                    String.class
            );
            System.out.println("📤 Submission Response: " + response.getBody());
        } catch (HttpClientErrorException e) {
            System.out.println("❌ Error: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
        }
    }
}