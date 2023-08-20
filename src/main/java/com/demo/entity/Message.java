package com.demo.entity;

import lombok.Data;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User buyer;
    private String role;
    @Column(length = 5000)
    private String content;
    private LocalDateTime createdTime;


}

