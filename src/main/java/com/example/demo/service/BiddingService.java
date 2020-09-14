package com.example.demo.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.example.demo.Config.GetTimeZone;
import com.example.demo.Model.Bidding;
import com.example.demo.Model.Request;
import com.example.demo.Model.Seller;
import com.example.demo.repository.master.BiddingRepository;
import com.example.demo.repository.master.RequestRepository;
import com.example.demo.repository.master.SellerRepository;

import org.springframework.boot.origin.SystemEnvironmentOrigin;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BiddingService {

    private final BiddingRepository biddingRepository;
    private final SellerRepository sellerRepository;
    private final RequestRepository requestRepository;

    public String insertBidding(
        int price,
        long sellerId,
        long requestId,
        String state
    ) {

        Optional<Request> opsRequest = requestRepository.findById(requestId);
        Optional<Seller> opsSeller = sellerRepository.findById(sellerId);
        
        if(opsRequest.isEmpty() || opsSeller.isEmpty()) {
            return "id가 없다";
        }
       
        if(this.getBiddingByRequest(requestId).size() > 9) {
            return "입찰 10개 이상 ";
        }
        Bidding nBidding = null;
        nBidding = biddingRepository.findBySellerIdAndRequestId(sellerId, requestId);
        if(nBidding != null) {
            return "이미 입찰했음";
        }

        Seller seller = opsSeller.get();
        Request request = opsRequest.get();

        nBidding = new Bidding();
        nBidding.setPrice(price);
        nBidding.setSeller(seller);
        nBidding.setRequest(request);
        nBidding.setState(state);
        nBidding.setUploadAt(GetTimeZone.StringToDate(GetTimeZone.getSeoulDate()));
        return  biddingRepository.save(nBidding).getId().toString();
    }

    public List<Map<String,Object>> getBiddingByRequest(long requestId) {
        return biddingRepository.getBiddingByRequest(requestId);
        
    }
    public List<Map<String,Object>> getBiddingBySellerId(long sellerId) {
        return biddingRepository.getBiddingBySeller(sellerId);
    }

    public void deleteBidding(long biddingId) {
        biddingRepository.deleteById(biddingId);
    }

    @Transactional
    public void tradeCancel(long requestId) {
        Request request = requestRepository.findById(requestId).get();
        request.setState("취소된 거래");
        List<Bidding> list = request.getBidding();
        Bidding bidding = null;
        for(int i = 0; i < list.size(); ++i) {
            bidding =  list.get(i);
            bidding.setState("취소된 거래");
            biddingRepository.save(bidding);
        }
        requestRepository.save(request);

    } 

    @Transactional
    public void choice(long requestId, long biddingId) {
        Request request = requestRepository.findById(requestId).get();
        request.setState("거래 진행중");
        List<Bidding> list = request.getBidding();
        Bidding bidding = null;
        for(int i = 0; i < list.size(); ++i) {

            bidding =  list.get(i);
            if(bidding.getId() == biddingId) {
                bidding.setState("거래 진행중");
            }
            else {
                bidding.setState("유찰");
            }
            biddingRepository.save(bidding);
        }
        requestRepository.save(request);
    } 

    //7
    @Transactional
    public void tradeComplete(long biddingId) {
        
        Bidding bidding = biddingRepository.findById(biddingId).get();
        if(bidding == null) {
            return;
        }
        
        Request request = bidding.getRequest();
        System.out.print("125");
        bidding.setState("거래 완료");
        request.setState("거래 완료");

        biddingRepository.save(bidding);
        requestRepository.save(request);
    } 
    //8
    @Transactional
    public void cancel(long biddingId) {
        Bidding bidding = biddingRepository.findById(biddingId).get();
        if(bidding == null) {
            return;
        }
        bidding.setState("취소된 거래");
        Request request = bidding.getRequest();
        request.setState("취소된 거래");
        biddingRepository.save(bidding);
        requestRepository.save(request);
    } 

    
}