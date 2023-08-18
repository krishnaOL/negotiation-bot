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
    @JoinColumn(name = "car_id")
    private Car car;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User buyer;

    private double bidAmount;
    private BidStatus status; // PENDING, ACCEPTED, REJECTED

    // Other bid details
    public Bid(Car car, User buyer, double bidAmount) {
        this.car = car;
        this.buyer = buyer;
        this.bidAmount = bidAmount;
    }


}
