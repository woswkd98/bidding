package com.example.demo.service;

import java.util.List;

import com.example.demo.common.*;
import com.example.demo.Model.Request;
import com.example.demo.repository.master.ReqHasTagRepository;
import com.example.demo.repository.master.RequestRepository;
import com.example.demo.repository.master.TagRepository;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RequestService  {

    private final RequestRepository requestRepo;
    private final TagRepository tagRepository;
    private final ReqHasTagRepository reqHasTagRepository;
   
  

    
}