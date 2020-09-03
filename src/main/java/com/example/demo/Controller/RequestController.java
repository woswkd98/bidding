package com.example.demo.Controller;

import com.example.demo.Model.Request;
import com.example.demo.VO.RequestGetter;
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
        
            return ResponseEntity.ok().body(requestService.insert(req).getId());
      }
      @RequestMapping(value = "/requests/{requestId}", method = RequestMethod.DELETE)
      public ResponseEntity<?> deleteRequest(@PathVariable long requestId) {
            
            
            return new ResponseEntity<>(requestService.delete(requestId), HttpStatus.OK);
      }

      @RequestMapping(value = "/requests/category/{category}", method = RequestMethod.GET)
      public ResponseEntity<?> findByCategory(@PathVariable String category) {
            return ResponseEntity.ok().body( requestService.findByCategory(category));
      }

      @RequestMapping(value = "/requests/tag/{tag}", method = RequestMethod.GET)
      public ResponseEntity<?> findByTag(@PathVariable String tag) {
            return ResponseEntity.ok().body(requestService.findByTag(tag));
      }

      
      @RequestMapping(value = "/requests/id/{id}", method = RequestMethod.GET)
      public ResponseEntity<?> findById(@PathVariable long id) {
            return ResponseEntity.ok().body(requestService.findById(id));
      }
      /*
      @RequestMapping(value = "/requests/{page}/{size}", method = RequestMethod.GET)
      public ResponseEntity<?> paging(
         @PathVariable int size,
         @PathVariable int page
      ) {
         return ResponseEntity.ok().body(requestService.paging(size, page));
      }
      */
      @RequestMapping(value = "/requests/{start}/{size}", method = RequestMethod.GET)
      public ResponseEntity<?> getRequestsPaged( 
            @PathVariable int start,
            @PathVariable int size
      ) {
            return ResponseEntity.ok().body(requestService.getRequestsPaged(start, size));
      }
      
      @RequestMapping(value = "/requests/{id}", method = RequestMethod.PATCH)
      public ResponseEntity<?> requestTimeOver( 
            @PathVariable int id
      ) {
            return ResponseEntity.ok().body(requestService.requestTimeOver(id));
      }
      
}   