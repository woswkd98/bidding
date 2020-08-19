package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.example.demo.Config.GetTimeZone;
import com.example.demo.Model.ReqHasTag;
import com.example.demo.Model.Request;
import com.example.demo.Model.Tag;
import com.example.demo.Model.User;
import com.example.demo.VO.RequestGetter;
import com.example.demo.repository.master.*;

import com.fasterxml.jackson.databind.deser.ValueInstantiator.Gettable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;



@Component
public class RequestResolver  implements GraphQLResolver<Request> {

    @Autowired
    private  RequestRepository requestRepository;
    @Autowired

    private  TagRepository tagRepository;
    @Autowired
    private  ReqHasTagRepository reqHasTagRepository;
    @Autowired
    private  UserRepository userRepository;
    /*
    query GetAllRequests{
        getAllRequests{
          _id
          author {
            _id
            name
          }
          detail
          category
          requestedAt
          deadLine
          hopeDate
          state
          tags
        }
      }
      */
      /*
    @Transactional
    public Request insertRequest( 
        Long userId,
        String detail, 
        String category,
        Long deadLine,
        String hopeDate,
        String[] tags) {
            
        Request request = new Request();
        request.setCategory(category);
        request.setDetail(detail);
        request.setDeadline(deadLine);
        request.setHopeDate(GetTimeZone.StringToDate(hopeDate));
        request.setUploadAt(GetTimeZone.StringToDate(GetTimeZone.getSeoulDate()));
        request.setUser_userId(userId);
        requestRepository.save(request);
            
        Tag tag = null;
        ReqHasTag rht = null;
    
        for(int i =0; i < tags.length; ++i) {
            tag = new Tag();
            tag.setBidCount(0);
            tag.setContext(tags[i]);
            tag.setRequestCount(0);
            tagRepository.save(tag);

            rht = new ReqHasTag();
            rht.setRequest(request);
            rht.setTag(tag);
            reqHasTagRepository.save(rht);
        }
        return request;
    }

    public List<RequestGetter> getRequests() {

        List<Request> requests = requestRepository.findAll();

        List<RequestGetter> getter = new ArrayList<RequestGetter>();
        RequestGetter rg = new RequestGetter(); 
        int size = requests.size();
        Request tempRq = null;
        for(int i =0; i < size; ++i) {
            tempRq = requests.get(i);
            rg.setRequest(tempRq);
            User user = userRepository.findById(tempRq.getUser_userId()).orElse(null);
            if(user == null) {
                System.out.println("getRequests 여기서 유저를 못찾음");
                return null;
            }
            rg.setUserId(user.getUserId());
            rg.setUserName(user.getUserName());
            rg.setTags(tagRepository.getTagContextsByRequest(tempRq.getRequestId()));
            getter.add(rg);
        }
           
           
        return getter;
       
    }*/


}