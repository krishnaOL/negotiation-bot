package com.demo.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.demo.entity.ChatMessages;
import com.demo.entity.NegotiateChatEntity;
import com.demo.entity.User;
import com.demo.model.Message;
import com.demo.model.NegotiateApiResponse;
import com.demo.model.OpenAIRequest;
import com.demo.model.OpenAIResponse;
import com.demo.model.Role;
import com.demo.repository.ChatMessagesRepo;
import com.demo.repository.NegotiateChatRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@Service
public class NegotiateChatService {
	Logger logger = LoggerFactory.getLogger(NegotiateChatService.class);

	@Qualifier("openaiRestTemplate")
	@Autowired
	private RestTemplate restTemplate;

	@Value("${openai.model}")
	private String model;

	@Value("${openai.api.url}")
	private String apiUrl;

	@Autowired
	NegotiateChatRepo negotiateChatRepo;

	@Autowired
	ChatMessagesRepo chatMessagesRepo;

	/**
	 * @param model
	 * @param prompt
	 * @return
	 */
	public NegotiateApiResponse processUserPrompt(String model, String prompt) {
		logger.info("inside processUserPrompt service");
		OpenAIResponse response = null;
		List<Message> messages = new ArrayList<Message>();
		messages.add(new Message(Role.Buyer.toString(), prompt));
		OpenAIRequest request = new OpenAIRequest(model, messages);
		NegotiateApiResponse res = new NegotiateApiResponse();

		try {
			response = restTemplate.postForObject(apiUrl, request, OpenAIResponse.class);
		} catch (Exception ex) {
			logger.error("Error in post request to OpenAi {}", ex.getMessage());
			res.setError(ex.getMessage());
			// return res;
		}
		NegotiateChatEntity chatEntityObj = populateEntityObj(prompt, response);
		saveChatData(chatEntityObj);

		if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
			res.setError("No response");
			return res;
		}

		/*
		 * NegotiateChatEntity chatEntityObj = populateEntityObj(prompt, response);
		 * saveChatData(chatEntityObj);
		 */
		res.setAnswer(response.getChoices().get(0).getMessage().getContent());
		return res;
	}

	/**
	 * @param model
	 * @param prompt
	 * @param buyerId
	 * @return
	 */
	public NegotiateApiResponse processChatMessage(String model, String prompt, long buyerId) {
		logger.info("inside processUserPrompt service");
		OpenAIResponse response = null;
		List<Message> messages = new ArrayList<Message>();
		ChatMessages chatmsg = new ChatMessages();
		chatmsg.setMessage(prompt);
		/* Get CHatHistory From DB */
		List<ChatMessages> chatHistMsges = chatMessagesRepo.findAllBySenderOrderByDate(buyerId);
		
		/*
		 * String chatReq = chatHistMsges.stream().map(msg -> messages.add(new
		 * Message("user", msg.get.getMessage())).collect(Collectors.joining()); chatReq
		 * = chatReq.concat(prompt);
		 */
		
		chatHistMsges.forEach(chatHist -> {
			messages.add(new Message(Role.Buyer.toString(), chatHist.getMessage()));
			messages.add(new Message(Role.Seller.toString(), chatHist.getChatResponse()));
		});
		messages.add(new Message(Role.Buyer.toString(), prompt));

		OpenAIRequest request = new OpenAIRequest(model, messages);
		NegotiateApiResponse res = new NegotiateApiResponse();

		try {
			response = restTemplate.postForObject(apiUrl, request, OpenAIResponse.class);
		} catch (Exception ex) {
			logger.error("Error in post request to OpenAi {}", ex.getMessage());
			res.setError(ex.getMessage());
			// return res;
		}
		logger.info("post done");

		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String chatReq = "";
		try {
			chatReq = ow.writeValueAsString(messages);
		} catch (JsonProcessingException e) {
			logger.error("Error in JsonProcessing {}", e.getMessage());
		}

		ChatMessages chatEntityObj = populateChatMessgEntity(prompt, chatReq, buyerId, response);
		saveChatMessageData(chatEntityObj);

		if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
			res.setError("No response");
			return res;
		}

		/*
		 * ChatMessages chatEntityObj = populateChatMessgEntity(prompt, chatReq,
		 * buyerId, response); saveChatMessageData(chatEntityObj);
		 */
		
		res.setAnswer(response.getChoices().get(0).getMessage().getContent());
		return res;
	}

	/**
	 * @param prompt
	 * @param response
	 * @return
	 */
	private NegotiateChatEntity populateEntityObj(String prompt, OpenAIResponse response) {

		NegotiateChatEntity entity = new NegotiateChatEntity();
		entity.setModel(model);
		entity.setRequest(prompt);
		if (response != null && response.getChoices() != null && !response.getChoices().isEmpty()) {
		entity.setResponse(response.getChoices().get(0).getMessage().getContent());
		}
		entity.setUser("Buyer");
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = new Date();
		entity.setCreatedDate(formatter.format(date));
		return entity;
	}

	private ChatMessages populateChatMessgEntity(String prompt, String chatReq, long buyerId, OpenAIResponse response) {
		logger.info("populateChatMessgEntity");
		User user = new User();
		user.setId(buyerId);
		ChatMessages chatmsg = new ChatMessages();
		chatmsg.setChatRequest(chatReq);
		if (response != null && response.getChoices() != null && !response.getChoices().isEmpty()) {
			chatmsg.setChatResponse(response.getChoices().get(0).getMessage().getContent());
		}
		chatmsg.setMessage(prompt);
		chatmsg.setSender(user);
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = new Date();
		chatmsg.setCreatedDate(formatter.format(date));
		return chatmsg;

	}

	/**
	 * @param chatEntityObj
	 */
	public void saveChatData(NegotiateChatEntity chatEntityObj) {
		try {
			negotiateChatRepo.save(chatEntityObj);

		} catch (Exception e) {

		}
	}

	/**
	 * @param chatEntityObj
	 */
	public void saveChatMessageData(ChatMessages chatEntityObj) {
		try {
			logger.info("saveChatMessageData");
			chatMessagesRepo.save(chatEntityObj);

		} catch (Exception e) {
			logger.error("saveChatMessageData {}", e.getMessage());
		}
	}

}
