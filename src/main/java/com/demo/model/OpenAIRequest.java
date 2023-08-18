package com.demo.model;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;


public class OpenAIRequest {

	private String model;
	private List<Message> messages;

	public OpenAIRequest(String model, String prompt) {
		this.model = model;
		this.messages = new ArrayList<>();
		this.messages.add(new Message("user", prompt));
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}
}
