package com.demo.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.demo.entity.NegotiateChatEntity;
import com.demo.model.NegotiateApiResponse;
import com.demo.model.OpenAIRequest;
import com.demo.model.OpenAIResponse;
import com.demo.repository.NegotiateChatRepo;


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

	/**
	 * @param model
	 * @param prompt
	 * @return
	 */
	public NegotiateApiResponse processUserPrompt(String model, String prompt) {
		logger.info("inside processUserPrompt service");
		OpenAIResponse response = null;
		OpenAIRequest request = new OpenAIRequest(model, prompt);
		NegotiateApiResponse res = new NegotiateApiResponse();
		
		try {
		response = restTemplate.postForObject(apiUrl, request, OpenAIResponse.class);
		}
		catch(Exception ex) {
			logger.error("Error in post request to OpenAi {}", ex.getMessage());
			res.setError(ex.getMessage());
			return res;
		}
		if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
			res.setError( "No response");
			return res;
		}

		NegotiateChatEntity chatEntityObj = populateEntityObj(prompt, response);
		saveChatData(chatEntityObj);
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
		entity.setResponse(response.getChoices().get(0).getMessage().getContent());
		entity.setUser("Buyer");
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = new Date();
		entity.setCreatedDate(formatter.format(date));
		return entity;
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

}
