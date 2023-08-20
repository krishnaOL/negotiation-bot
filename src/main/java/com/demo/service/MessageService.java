package com.demo.service;

import com.demo.entity.Message;
import com.demo.entity.User;
import com.demo.model.ChatRequest;
import com.demo.model.OpenAIResponse;
import com.demo.model.Role;
import com.demo.repository.MessageRepository;
import com.demo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @Value("${openai.api.key}")
    private String openaiApiKey;

    @Value("${openai.api.url}")
    private String openaiApiEndpoint;

    @Value("${openai.model}")
    private String model;

    public String chatAndStoreHistory(ChatRequest chatRequest) {
        Message userMessage = new Message();
        userMessage.setRole(Role.user.name());
        userMessage.setContent(chatRequest.getPrompt());
        User buyer = userRepository.findById(chatRequest.getBuyerId()).get();
        userMessage.setBuyer(buyer);
        if(messageRepository.findMessagesByBuyerIdAndContent(buyer.getId(), chatRequest.getPrompt()).isEmpty()) {
            userMessage.setCreatedTime(LocalDateTime.now());
            messageRepository.save(userMessage);
        }

        List<Message> conversationHistory = messageRepository.findMessagesByBuyerIdOrderByCreatedTimeAsc(buyer.getId());
        String aiResponse = getAiResponse(conversationHistory);

        Message aiMessage = new Message();
        aiMessage.setRole(Role.assistant.name());
        aiMessage.setBuyer(buyer);
        aiMessage.setContent(aiResponse);
        aiMessage.setCreatedTime(LocalDateTime.now());
        messageRepository.save(aiMessage);
        return aiResponse;
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    private String getAiResponse(List<Message> conversationHistory) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Bearer " + openaiApiKey);

        List<Map<String, String>> messages = new ArrayList<>();
        messages.add(Map.of("role", "system", "content", " You are an AI-driven seller's assistant. Your role is to help sellers negotiate deals with potential buyers. You can provide information about the listed items, suggest negotiation strategies, and work towards achieving mutually agreeable outcomes. Your goal is to assist the seller in reaching a successful deal."));
        for (Message message : conversationHistory) {
            messages.add(Map.of("role", message.getRole(), "content", message.getContent()));
        }

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", model);
        requestBody.put("messages", messages);
        log.info("Body for ChatGPT " + requestBody);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        OpenAIResponse response = restTemplate.postForObject(
                openaiApiEndpoint,
                requestEntity,
                OpenAIResponse.class
        );


        if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
            return "Error while processing the request";
        }
        return response.getChoices().get(0).getMessage().getContent();

    }
}
