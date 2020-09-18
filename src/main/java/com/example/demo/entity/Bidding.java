package com.example.demo.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import org.hibernate.annotations.ColumnDefault;

import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Data
@Entity(name = "bidding")
@Table(name = "bidding")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Bidding  {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // mysql에 자동증가 기능있는데 타입이 위임이므로 걔로 위임
    @Column(name = "bidding_id", nullable = false, unique = true)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "uploadAt", nullable = false)
    private Date uploadAt;

    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "state", nullable = false)
    @ColumnDefault("0")
    private String state;
    
    @ManyToOne
    @JoinColumn(name = "request_id")
    private Request request;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private Seller seller;

    
}