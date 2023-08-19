package com.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "ChatMessages")
public class ChatMessages {
	
	@Id
	@Column(name = "chatId")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long chatId;
	@Column(name = "message")
	private String message;
	@Column(name = "chatRequest")
	private String chatRequest;
	@Column(name = "chatResponse")
	private String chatResponse;
	@Column(name = "createdDate")
	private String createdDate;
	
	@Column(name = "role")
	private String role;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User sender;
	
	
	
	
	
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public long getChatId() {
		return chatId;
	}
	public void setChatId(long chatId) {
		this.chatId = chatId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getChatRequest() {
		return chatRequest;
	}
	public void setChatRequest(String chatReq) {
		this.chatRequest = chatReq;
	}
	public String getChatResponse() {
		return chatResponse;
	}
	public void setChatResponse(String chatResponse) {
		this.chatResponse = chatResponse;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public User getSender() {
		return sender;
	}
	public void setSender(User sender) {
		this.sender = sender;
	}
	
}
