package com.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Bid {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Car car;

    @ManyToOne
    private User buyer;

    private double bidAmount;

    private double maxBidAmount;

    private BidStatus status; // PENDING, ACCEPTED, REJECTED

    // Other bid details


}
