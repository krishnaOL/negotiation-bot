package com.demo.service;

import com.demo.entity.Car;
import com.demo.entity.Message;
import com.demo.model.ChatRequest;
import com.demo.model.OpenAIResponse;
import com.demo.model.Role;
import com.demo.repository.CarRepository;
import com.demo.repository.MessageRepository;
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
    private CarRepository carRepository;

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
        userMessage.setChatId(chatRequest.getChatId());
        if (messageRepository.findMessagesByChatIdAndContent(chatRequest.getChatId(), chatRequest.getPrompt()).isEmpty()) {
            userMessage.setCreatedTime(LocalDateTime.now());
            messageRepository.save(userMessage);
        }

        Car carDetails = carRepository.findById(chatRequest.getCarId()).get();


        List<Message> conversationHistory = messageRepository.findMessagesByChatIdOrderByCreatedTimeAsc(chatRequest.getChatId());
        String aiResponse = getAiResponse(conversationHistory, carDetails);

        Message aiMessage = new Message();
        aiMessage.setRole(Role.assistant.name());
        aiMessage.setChatId(chatRequest.getChatId());
        aiMessage.setContent(aiResponse);
        aiMessage.setCreatedTime(LocalDateTime.now());
        messageRepository.save(aiMessage);
        return aiResponse;
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    private String getAiResponse(List<Message> conversationHistory, Car car) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Bearer " + openaiApiKey);

        String carInformation = car.getYear() + " " + car.getMake() + " " + car.getModel();
        String terms = " I wish to sell a vehicle at the best feasible price, " +
                " which is as follows.Sell at a price that is higher than the asking price or Evaluate whether the " +
                " selling price falls within the range of 5% to 7% plus or minus. Notify me if the price falls below " +
                " 7% by 2%. If the requested price is less than 10% of the listed price, please provide alternative " +
                " vehicles that may fit the budget. Alternately, offer a free six-month or two-month warranty extension " +
                " and see if the customer will agree to the listed price plus or minus 5%. ";


        List<Map<String, String>> messages = new ArrayList<>();
        messages.add(Map.of("role", "system", "content", " You are an expert seller's assistant. " +
                "Your role is to assist sellers in negotiating deals with potential buyers based on their terms. " +
                "The seller's terms for the " + carInformation + " are as follows:" +
                "Your goal is to work within these terms and facilitate a successful deal. If an offer doesn't meet the terms, " + terms +
                "you can inform the buyer that you'll need to contact the seller for their response. " +
                "Do not disclose the sellers terms. " +
                "Don't Justify your answer. " +
                "Do not provide information not mentioned in the CONTEXT INFORMATION."));
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
