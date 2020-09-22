package com.example.demo.Controller;

import java.util.Map;

import com.example.demo.VO.BiddingVO;
import com.example.demo.VO.RequestGetter;
import com.example.demo.entity.Bidding;
import com.example.demo.service.BiddingService;
import com.example.demo.service.RequestService;

import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class BiddingController {

    private final BiddingService biddingService;

    @RequestMapping(value = "/biddings", method = RequestMethod.PUT)
    public ResponseEntity<?> insertRequest(@RequestBody BiddingVO req) {
        System.out.println(req.getSellerId());
        System.out.println(req.getPrice());
        System.out.println(req.getRequestId());

        return ResponseEntity.ok().body(
            biddingService.insertBidding(
                req.getPrice(), 
                req.getSellerId(),
                req.getRequestId(),
                req.getState()
            ));
    }

    @RequestMapping(value = "/biddings/request/{requestId}", method = RequestMethod.GET)
    public ResponseEntity<?> getBiddingByRequestId(@PathVariable long requestId) {
        
        return ResponseEntity.ok().body(
            biddingService.getBiddingByRequest(requestId)
        );
    }

    @RequestMapping(value = "/biddings/seller/{sellerId}", method = RequestMethod.GET)
    public ResponseEntity<?> getBiddingBySellerId(@PathVariable long sellerId) {
        System.out.println(sellerId);
        return ResponseEntity.ok().body(
            biddingService.getBiddingBySellerId(sellerId)
        );
    }

    @RequestMapping(value = "/biddings/{biddingId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteBidding(@PathVariable long biddingId) {
        biddingService.deleteBidding(biddingId);
        return new ResponseEntity<>("deleteOk", HttpStatus.OK);
    }

    @RequestMapping(value = "/biddings/tradeCancel", method = RequestMethod.POST)
    public ResponseEntity<?> tradeCancel(
        @RequestBody Map<String, Object> map
    ) {
        biddingService.tradeCancel(
            Long.valueOf(map.get("requestId").toString())
        );
        return new ResponseEntity<>("deleteOk", HttpStatus.OK);
    }

    @RequestMapping(value = "/biddings/biddingCancel", method = RequestMethod.POST)
    public ResponseEntity<?> cancel(
        @RequestBody Map<String, Object> map
    ) {
        biddingService.cancel(
            Long.valueOf(map.get("biddingId").toString())
        );
        return new ResponseEntity<>("deleteOk", HttpStatus.OK);
    }

    @RequestMapping(value = "/biddings/chioce", method = RequestMethod.POST)
    public ResponseEntity<?> choice(
        @RequestBody Map<String, Object> map
    ) {
        return new ResponseEntity<>(biddingService.choice(
            Long.valueOf(map.get("requestId").toString()),
            Long.valueOf(map.get("biddingId").toString())), HttpStatus.OK);
        }
 


    @RequestMapping(value = "/biddings/complete", method = RequestMethod.POST)
    public ResponseEntity<?> complete(
        @RequestBody Map<String, Object> map
    ) {
        //System.out.println(map.get("biddingId"));
        biddingService.tradeComplete( Long.valueOf(map.get("biddingId").toString()));
        return new ResponseEntity<>("complete", HttpStatus.OK);
    }

    
   
}



