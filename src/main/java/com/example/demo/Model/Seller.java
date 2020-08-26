package com.example.demo.Model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import lombok.Data;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
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

    @Column(name = "sellerGrage", nullable = true)
    private Long sellerGrage;

    @Column(name = "reviewCount", nullable = true)
    private Long reviewCount;

    @Column(name = "state", nullable = false)
    private String state;
    
    @OneToOne
    @JoinColumn(name ="user_id")
    private User user;
    
    @OneToMany(mappedBy = "seller") 
    private List<SellerHasImg> seller_Has_Imgs = new ArrayList<>();
    
    @OneToMany(mappedBy = "seller")
    private List<Review> review = new ArrayList<>();
    
}