package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.Model.Bidding;
import com.example.demo.Model.Request;
import com.example.demo.Model.Seller;
import com.example.demo.repository.master.BiddingRepository;
import com.example.demo.repository.master.RequestRepository;
import com.example.demo.repository.master.SellerRepository;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BiddingService {

    BiddingRepository biddingRepository;
    SellerRepository sellerRepository;
    RequestRepository requestRepository;

    public String insertBidding(
        int price,
        String context,
        long sellerId,
        long requestId
    ) {
        Request request = requestRepository.findById(requestId).get();
        Seller seller = sellerRepository.findById(sellerId).get();
        
        if(request == null || seller == null) {
            return "id가 없다";
        }

        if(this.getBiddingByRequest(requestId).size() > 9) {
            return "입찰 10개 이상 ";
        }

        Bidding nBidding = new Bidding();
        nBidding.setPrice(price);
        nBidding.setContext(context);
        nBidding.setSeller(seller);
        nBidding.setRequest(request);
       
        return  biddingRepository.save(nBidding).getId().toString();
    }

    public List<Bidding> getBiddingByRequest(long requestId) {
        return biddingRepository.getBiddingByRequest(requestId);
        
    }
    public List<Bidding> getBiddingBySellerId(long sellerId) {
        return biddingRepository.getBiddingBySeller(sellerId);
    }



}