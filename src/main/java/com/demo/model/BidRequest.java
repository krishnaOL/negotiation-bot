package com.demo.model;

import lombok.Data;

@Data
public class BidRequest {
	
	private long carId;
	private long buyerId;
	private double bidAmount;
	private double maxBidAmount;



}
