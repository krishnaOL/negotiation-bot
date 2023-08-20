package com.demo.model;

import lombok.Data;

import java.util.List;
@Data
public class OpenAIResponse {

	private List<Choice> choices;

}
