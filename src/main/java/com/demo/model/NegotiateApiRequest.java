package com.demo.model;

public class NegotiateApiRequest {
	
	private long buyerId;
	private String prompt;

	public String getPrompt() {
		return prompt;
	}

	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}

	public long getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(long buyerId) {
		this.buyerId = buyerId;
	}
	
	

}
