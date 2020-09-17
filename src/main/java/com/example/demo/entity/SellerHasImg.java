package com.example.demo.entity;

import java.io.Serializable;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "sellerHasImg")
@Table(name = "sellerHasImg")
public class SellerHasImg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // mysql에 자동증가 기능있는데 타입이 위임이므로 걔로 위임
    @Column(name = "sellerHasImg_id")
    private Long id ;

    @ManyToOne
    @JoinColumn(name = "images_id")
    private Images images;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private Seller seller;
}