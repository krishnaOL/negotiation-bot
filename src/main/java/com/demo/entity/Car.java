package com.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String make;
    private String model;
    private int year;
    private double minBidAmount;
    private boolean soldOut; // Indicates if the car is available for bidding
    private double acceptedBidAmount; // Stores the accepted bid amount
    @ManyToOne
    private User buyer; // Stores the buyer associated with the accepted bid


    // Other car details
}
