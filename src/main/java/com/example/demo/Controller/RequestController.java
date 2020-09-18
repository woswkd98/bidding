package com.example.demo.Controller;

import com.example.demo.DTO.RequestGetInfo;
import com.example.demo.VO.RequestGetter;
import com.example.demo.entity.Request;
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
public class RequestController {
    
      private final RequestService requestService;
    
      @RequestMapping(value = "/requests", method = RequestMethod.PUT)
      public ResponseEntity<?> insertRequest(@RequestBody RequestGetter req) {
            System.out.println(req.toString());
            
            return ResponseEntity.ok().body(requestService.insert(req));
      }
      @RequestMapping(value = "/requests/{requestId}", method = RequestMethod.DELETE)
      public ResponseEntity<?> deleteRequest(@PathVariable long requestId) {
            
            
            return new ResponseEntity<>(requestService.delete(requestId), HttpStatus.OK);
      }

      @RequestMapping(value = "/requests/{start}/{sortKey}", method = RequestMethod.GET)
      public ResponseEntity<?> getAllPaged(@PathVariable int start,  @PathVariable String sortKey) {

            
            return ResponseEntity.ok().body(requestService.getAllPaged(start,sortKey));
      }

      @RequestMapping(value = "/requests/category/{category}/{start}/{sortKey}", method = RequestMethod.GET)
      public ResponseEntity<?> findByCategory(
            @PathVariable String category,
            @PathVariable int start,
            @PathVariable String sortKey

      ) {
            return ResponseEntity.ok().body( requestService.getRequestsPaged(start, category,sortKey));
      }

      @RequestMapping(value = "/requests", method = RequestMethod.POST)
      public ResponseEntity<?> getRequests(
           @RequestBody RequestGetInfo requestGetInfo
      ) {
            System.out.println(requestGetInfo.toString());
            return ResponseEntity.ok().body( 
                  requestService.getRequestByTag(
                        requestGetInfo.getPage(), 
                        requestGetInfo.getCategory(),
                        requestGetInfo.getOrderName(),
                        requestGetInfo.getInputTag(),
                        requestGetInfo.isOrderPos()
                  ));
      }

      @RequestMapping(value = "/requests/tag/{tag}", method = RequestMethod.GET)
      public ResponseEntity<?> findByTag(@PathVariable String tag) {
            return ResponseEntity.ok().body(requestService.findByTag(tag));
      }

      
      @RequestMapping(value = "/requests/userId/{userId}", method = RequestMethod.GET)
      public ResponseEntity<?> findById(@PathVariable long userId) {
            return ResponseEntity.ok().body(requestService.getRequestByUserId(userId));
      }

      
      @RequestMapping(value = "/requests/requestId/{requestId}", method = RequestMethod.GET)
      public ResponseEntity<?> getRequestByRequestId(@PathVariable long requestId) {
            return ResponseEntity.ok().body(requestService.getRequestById(requestId));
      }
   
      
      @RequestMapping(value = "/requests/{id}", method = RequestMethod.PATCH)
      public ResponseEntity<?> requestTimeOver( 
            @PathVariable long id
      ) {
            return ResponseEntity.ok().body(requestService.requestTimeOver(id));
      }
      
}   