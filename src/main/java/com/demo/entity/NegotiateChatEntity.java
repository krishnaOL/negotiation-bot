package com.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "NegotiateChatData")
public class NegotiateChatEntity {

	@Id
	@Column(name = "chatId")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long chatId;

	@Column(name = "model")
	private String model;

	@Column(name = "request")
	private String request;

	@Column(name = "response")
	private String response;

	@Column(name = "chatUser")
	private String user;

	@Column(name = "createdDate")
	private String createdDate;

	/*
	 * public NegotiateChatEntity(String model, String request, String response,
	 * String user, String createdDate) { this.model = model; this.request =
	 * request; this.response = response; this.user = user; this.createdDate =
	 * createdDate; }
	 */

	public NegotiateChatEntity() {

	}

	public long getChatId() {
		return this.chatId;
	}

	public void setChatId(long chatId) {
		this.chatId = chatId;
	}

	public String getRequest() {
		return this.request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public String getResponse() {
		return this.response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}



}