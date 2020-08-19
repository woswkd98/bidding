package com.example.demo.Model;
import java.util.Date;

import javax.persistence.*;

import lombok.Data;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Data
@Entity(name ="seller")
@NoArgsConstructor
@Getter
@Setter
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // mysql에 자동증가 기능있는데 타입이 위임이므로 걔로 위임
    @Column(name = "sellerId", nullable = false, unique = true)
    private Long sellerId;
    
    @Column(name = "portfolio", nullable = false)
    private String portfolio;
    
    @Column(name = "imageLink", nullable = false)
    private String imageLink;
    

    @Column(name = "imageCount", nullable = false)
    private int imageCount;
    
    @Column(name = "sellerGrage", nullable = true)
    private Long sellerGrage;

    @Temporal(TemporalType.DATE)
    @Column(name = "reviewCount", nullable = true)
    private Long reviewCount;

    @Column(name = "state", nullable = false)
    private String state;
    
    @Column(name = "user_userId", nullable = false)
    private String user_userId;
}