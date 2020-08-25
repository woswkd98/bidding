package com.example.demo.Model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import lombok.Data;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Data
@NoArgsConstructor
@Getter
@Setter
@Entity(name ="seller")
@Table(name = "seller")
public class Seller {
 

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // mysql에 자동증가 기능있는데 타입이 위임이므로 걔로 위임
    @Column(name = "seller_id", nullable = false, unique = true)
    private Long id;
    
    @Column(name = "portfolio", nullable = false)
    private String portfolio;
    
    @Column(name = "imageLink", nullable = false)
    private String imageLink;
    

    @Column(name = "imageCount", nullable = false)
    private int imageCount;
    
    @Column(name = "sellerGrage", nullable = true)
    private Long sellerGrage;


    @Column(name = "reviewCount", nullable = true)
    private Long reviewCount;

    @Column(name = "state", nullable = false)
    private String state;
    
    @OneToOne
    @JoinColumn(name ="user_id")
    private User user;
  
    
}