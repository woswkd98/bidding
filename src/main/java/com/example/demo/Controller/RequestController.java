package com.example.demo.Controller;

import com.example.demo.VO.RequestGetter;
import com.example.demo.service.RequestService;

import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class RequestController {
    
    private final RequestService requestService;
    /*
    @RequestMapping(value = "/requests", method = RequestMethod.PUT)
    public ResponseEntity<?> insertRequest(RequestEntity<RequestGetter> req) {
       return ResponseEntity.ok().body(requestService.insert(req.getBody()).getId());
    }
    @RequestMapping(value = "/requests", method = RequestMethod.DELETE)
    public ResponseEntity<?> insertRequest(RequestEntity<RequestGetter> req) {
       return ResponseEntity.ok().body(requestService.insert(req.getBody()).getId());
    }

*/
    
}   