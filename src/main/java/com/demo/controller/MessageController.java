package com.demo.controller;

import com.demo.entity.Message;
import com.demo.model.ChatRequest;
import com.demo.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @CrossOrigin(origins = "*")
    @PostMapping
    public String chat(@RequestBody ChatRequest chatRequest) {
        return messageService.chatAndStoreHistory(chatRequest);
    }

    @GetMapping
    public List<Message> getAllMessages() {
        return messageService.getAllMessages();
    }
}
