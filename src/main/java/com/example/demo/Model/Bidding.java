package com.example.demo.Model;
import java.util.Date;

import javax.persistence.*;

import lombok.Data;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Data
@Entity(name = "bidding")
@NoArgsConstructor
@Getter
@Setter
public class Bidding {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // mysql에 자동증가 기능있는데 타입이 위임이므로 걔로 위임
    @Column(name = "biddingId", nullable = false, unique = true)
    private Long biddingId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "uploadAt", nullable = false)
    private Date uploadAt;
    
    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "state", nullable = false)
    private String state;
    
    @Column(name = "request_requestId", nullable = false)
    private Long request_requestId;

    @Column(name = "user_userId", nullable = false)
    private Long user_userId;

    
}