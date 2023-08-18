package com.demo.model;

import lombok.Data;

@Data
public class BidResponse {

    private boolean accepted;
    private double counterOfferAmount;
    private String message;
    private String negotiationStage;
    private String negotiationDetails;
    private String timestamp;

}
