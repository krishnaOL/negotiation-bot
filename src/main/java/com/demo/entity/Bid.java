package com.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "bid")
public class Bid {

    @Id
    @Column(name = "bid_Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Car car;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User buyer;

    @Column(name = "bidAmount")
    private double bidAmount;

    @Column(name = "maxBidAmount")
    private double maxBidAmount;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private BidStatus status; // PENDING, ACCEPTED, REJECTED

    // Other bid details


}
