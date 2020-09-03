package com.example.demo.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*

    @Column(name = "context", nullable = false)
    private String context;
    
    @Column(name = "price", nullable = false)
    private int price;

    @ManyToOne
    @JoinColumn(name = "request_id")
    private Request request;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private Seller seller;

*/
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BiddingVO {
    private String context;
    private int price;
    private long requestId;
    private long sellerId;
    private String state;
}