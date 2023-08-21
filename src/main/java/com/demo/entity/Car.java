package com.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "car")
public class Car {
    @Id
    @Column(name = "car_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "make")
    private String make;
    @Column(name = "terms")
    private String terms;
    @Column(name = "model")
    private String model;
    @Column(name = "car_year")
    private int year;
    @Column(name = "minBidAmount")
    private double minBidAmount;
    @Column(nullable = true)
    private Boolean soldOut; // Indicates if the car is available for bidding
    @Column(nullable = true)
    private Double acceptedBidAmount; // Stores the accepted bid amount
    @JoinColumn(nullable = true)
    @ManyToOne
    private User buyer; // Stores the buyer associated with the accepted bid
    @Column(name= "terms" , nullable = true)
    private String terms;
    
	public String getTerms() {
		return terms;
	}
	public void setTerms(String terms) {
		this.terms = terms;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMake() {
		return make;
	}
	public void setMake(String make) {
		this.make = make;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public double getMinBidAmount() {
		return minBidAmount;
	}
	public void setMinBidAmount(double minBidAmount) {
		this.minBidAmount = minBidAmount;
	}
	public Boolean getSoldOut() {
		return soldOut;
	}
	public void setSoldOut(Boolean soldOut) {
		this.soldOut = soldOut;
	}
	public Double getAcceptedBidAmount() {
		return acceptedBidAmount;
	}
	public void setAcceptedBidAmount(Double acceptedBidAmount) {
		this.acceptedBidAmount = acceptedBidAmount;
	}
	public User getBuyer() {
		return buyer;
	}
	public void setBuyer(User buyer) {
		this.buyer = buyer;
	}





    // Other car details

    // Other car details
    
    
}
