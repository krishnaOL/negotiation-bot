package com.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class BidResponse {

    private boolean accepted;
    private double counterOfferAmount;
    private String message;
    private String negotiationStage;
    private String negotiationDetails;
    private LocalDateTime timestamp;

}
