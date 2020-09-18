package com.example.demo.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.ColumnDefault;

import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
@Data

@NoArgsConstructor
@AllArgsConstructor
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

    @Column(name = "sumGrage", nullable = true)
    @ColumnDefault("0") //default 0
    private float sumRate;

    @Column(name = "reviewCount", nullable = true)
    @ColumnDefault("0") //default 0
    private Long reviewCount;

   
    @Column(name = "state", nullable = true)
    @ColumnDefault("0") //default 0
    private String state;
    
    @OneToOne
    @JoinColumn(name ="user_id")
    private User user;
    
    @JsonIgnore
    @OneToMany(mappedBy = "seller") 
    private List<SellerHasImg> sellerHasImgs = new ArrayList<>();
    
    @JsonIgnore
    @OneToMany(mappedBy = "seller")
    private List<Review> review = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "seller")
    private List<Bidding> bidding = new ArrayList<>();
    
}