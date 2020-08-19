package com.example.demo.Model;
import java.util.Date;

import javax.persistence.*;
import lombok.Data;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Data
@Entity(name ="review")
@NoArgsConstructor
@Getter
@Setter
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // mysql에 자동증가 기능있는데 타입이 위임이므로 걔로 위임
    @Column(name = "reviewId", nullable = false, unique = true)
    private Long reviewId;

    @Column(name = "grade", nullable = true)
    private float grade;

    @Column(name = "context", nullable = false)
    private String context;
    
    @Column(name = "seller_sellerId", nullable = false)
    private Long request_requestId;

    @Column(name = "user_userId", nullable = false)
    private Long user_userId;
}