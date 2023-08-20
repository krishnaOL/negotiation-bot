package com.demo.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChatRequest {
	
	private long buyerId;
	private String prompt;

}
