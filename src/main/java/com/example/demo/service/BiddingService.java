package com.example.demo.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.example.demo.Config.GetTimeZone;
import com.example.demo.DTO.BiddingRequestGetter;
import com.example.demo.entity.Bidding;
import com.example.demo.entity.QBidding;
import com.example.demo.entity.QRequest;
import com.example.demo.entity.QSeller;
import com.example.demo.entity.QUser;
import com.example.demo.entity.Request;
import com.example.demo.entity.Seller;
import com.example.demo.repository.master.BiddingRepository;
import com.example.demo.repository.master.RequestRepository;
import com.example.demo.repository.master.SellerRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

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
    private final JPAQueryFactory factory;
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

    public List<BiddingRequestGetter> getBiddingByRequest(long requestId) {
        QBidding bidding = QBidding.bidding;
        QRequest request = QRequest.request;
        QUser user = QUser.user;
        QSeller seller = QSeller.seller;
  
        return factory.select(Projections.constructor(BiddingRequestGetter.class, 
        bidding.id,
        bidding.price, 
        request.id, 
        seller.id, 
        bidding.state, 
        seller.user.userEmail,
        seller.user.userName, 
        seller.user.id, 
        request.detail, 
        request.category,
        request.deadline,
        request.hopeDate,
        request.uploadAt, 
        request.state,
        seller.user.images.url)).from(request)
            .innerJoin(request.user, user)
            .innerJoin(request.bidding, bidding)
            .innerJoin(bidding.seller, seller)
            .where(request.id.eq(requestId)).fetch();
        
        
    }
    public List<BiddingRequestGetter> getBiddingBySellerId(long sellerId) {
           /*
           private long bidding_id;
    private int price;
    private long request_id;
    private long seller_id;
    private String bid_state;
    private String user_email;
    private String user_name;
    private String user_id;
    private String detail;
    private String category;
    private String deadline;
    private String hope_date;
    private String upload_at;
    private String request_state;
        */
        QBidding bidding = QBidding.bidding;
        QRequest request = QRequest.request;
        QUser user = QUser.user;
        QSeller seller = QSeller.seller;
  
        return factory.select(Projections.constructor(BiddingRequestGetter.class, 
        bidding.id,
        bidding.price, 
        request.id, 
        seller.id, 
        bidding.state, 
        seller.user.userEmail,
        seller.user.userName, 
        seller.user.id, 
        request.detail, 
        request.category,
        request.deadline,
        request.hopeDate,
        request.uploadAt, 
        request.state,
        seller.user.images.url)).from(request)
            .innerJoin(request.user, user)
            .innerJoin(request.bidding, bidding)
            .innerJoin(bidding.seller, seller)
            .where(seller.id.eq(sellerId)).fetch();
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
    public Bidding choice(long requestId, long biddingId) {
        Request request = requestRepository.findById(requestId).get();
        request.setState("거래 진행중");
        List<Bidding> list = request.getBidding();
        Bidding bidding = null;
        Bidding temp = null;
        for(int i = 0; i < list.size(); ++i) {

            bidding =  list.get(i);

            if(bidding.getId() == biddingId) {
                bidding.setState("거래 진행중");
                temp = bidding;
            }
            else {
                bidding.setState("유찰");
            }
            biddingRepository.save(bidding);
        }
      requestRepository.save(request);
     
      return temp;
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