package com.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.demo.model.NegotiateApiRequest;
import com.demo.model.NegotiateApiResponse;
import com.demo.service.NegotiateChatService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class NegotialeApiController {
    Logger logger = LoggerFactory.getLogger(NegotialeApiController.class);

    @Autowired
    NegotiateChatService negotiateChatservice;

    @Value("${openai.model}")
    private String model;

    /**
     * Creates a chat request and sends it to the OpenAI API Returns the first
     * message from the API response
     *
     * @param prompt the prompt to send to the API
     * @return first message from the API response
     */
    @PostMapping("/api/chat")
    public ResponseEntity<NegotiateApiResponse> chat(@RequestBody NegotiateApiRequest req) {
        logger.info("inside chat controller: prompt = {}", req.getPrompt());       
        NegotiateApiResponse chatResponse = negotiateChatservice.processChatMessage(model, req.getPrompt(), req.getBuyerId());
       // return ResponseEntity.ok(cars);
        if (!chatResponse.getError().isEmpty()) {
            return new ResponseEntity<>(chatResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(chatResponse, HttpStatus.OK);
    }
    
    @PostMapping("/chat")
    public ResponseEntity<NegotiateApiResponse> chatApi(@RequestBody NegotiateApiRequest req) {
        logger.info("inside chat controller: prompt = {}", req.getPrompt());
        NegotiateApiResponse chatResponse = negotiateChatservice.processUserPrompt(model, req.getPrompt());
        if (!chatResponse.getError().isEmpty()) {
            return new ResponseEntity<>(chatResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(chatResponse, HttpStatus.OK);
    }

}
