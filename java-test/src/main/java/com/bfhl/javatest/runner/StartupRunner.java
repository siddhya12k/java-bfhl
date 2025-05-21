package com.bfhl.javatest.runner;

import com.bfhl.javatest.model.WebhookRequest;
import com.bfhl.javatest.model.WebhookResponse;
import com.bfhl.javatest.service.WebhookService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StartupRunner implements CommandLineRunner {

    private final WebhookService webhookService;

    public StartupRunner(WebhookService webhookService) {
        this.webhookService = webhookService;
    }

    @Override
    public void run(String... args) {
        System.out.println("🔥🔥🔥 StartupRunner is running! 🔥🔥🔥");

        // Replace with your actual details
        WebhookRequest request = new WebhookRequest("Siddhya", "REG12347", "siddhya@example.com");

        try {
            WebhookResponse response = webhookService.generateWebhook(request);
            System.out.println("✅ Webhook URL: " + response.getWebhook());
            System.out.println("✅ Access Token: " + response.getAccessToken());

            // ✅ Replace with actual SQL once you've solved the problem
            String finalQuery = "SELECT * FROM employees WHERE salary > 50000;";
            webhookService.submitFinalQuery(response.getAccessToken(), response.getWebhook(), finalQuery);

        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }
}
