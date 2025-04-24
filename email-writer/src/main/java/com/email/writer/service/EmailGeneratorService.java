package com.email.writer.service;

import com.email.writer.model.EmailBody;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
public class EmailGeneratorService {

    private WebClient client;

    public EmailGeneratorService(WebClient.Builder clientBuilder) {
        this.client = clientBuilder.build();
    }

    @Value("${gemini.api.uri}")
    private String apiURI;

    @Value("${gemini.api.key}")
    private String apiKey;

    public String generateEmailReply(EmailBody email){
        //BuildPrompt
        String prompt = buildEmailRequest(email);

        //craft request
        Map<String, Object> requestBody = Map.of(
                "contents" , new Object[]{
                        Map.of(
                                "parts" , new Object[]{
                                        Map.of("text", prompt)                                }
                        )
                }
        );
        

        //Do request and get response
        String response = client.post()
                .uri(apiURI + apiKey)
                .header("Content-Type" , "application/json")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();


        //retrun respone
        return extractResponse(response);
    }

    private String extractResponse(String response) {
        try{
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(response);
            return rootNode.path("candidates").get(0).
                    path("content").path("parts")
                    .get(0).path("text").asText();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    private String buildEmailRequest(EmailBody email) {
        StringBuilder prompt = new StringBuilder();
        // Base instruction
        prompt.append("You are a professional email assistant. ");
        prompt.append("Generate only the email content as a reply to the message below. ");
        prompt.append("Do not include a subject line, headers, or any explanations. ");
        prompt.append("Only output the reply content.\n\n");

        // Add tone if provided
        String tone = email.getTone();
        if (tone != null && !tone.isEmpty()) {
            prompt.append(String.format("Use a %s tone.\n\n", tone));
        }

        // Add the actual email message
        String content = email.getContent();
        if (content != null && !content.isEmpty()) {
            prompt.append("Original message:\n");
            prompt.append("\"").append(content).append("\"\n\n");
            prompt.append("Your reply:");
        }

        return prompt.toString();
    }
}
