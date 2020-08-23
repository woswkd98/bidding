package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
//import com.coxautodev.graphql.tools.GraphQLResolver;
import com.example.demo.Config.GetTimeZone;
import com.example.demo.Model.ReqHasTag;
import com.example.demo.Model.Request;
import com.example.demo.Model.Tag;
import com.example.demo.Model.User;
import com.example.demo.VO.RequestGetter;
import com.example.demo.VO.TestId;
import com.example.demo.repository.master.*;

import com.fasterxml.jackson.databind.deser.ValueInstantiator.Gettable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import graphql.ExecutionResult;
import lombok.RequiredArgsConstructor;



@Component
public class RequestResolver  implements GraphQLQueryResolver {
    private final RequestRepository requestRepository;
    private final TagRepository tagRepository;
    private final ReqHasTagRepository reqHasTagRepository;
    private final UserRepository userRepository;
    
    @Autowired
    public  RequestResolver( 
        RequestRepository requestRepository,
        TagRepository tagRepository, 
        ReqHasTagRepository reqHasTagRepository,
        UserRepository userRepository
    ) {

        this.requestRepository = requestRepository;
        this.tagRepository = tagRepository;
        this.reqHasTagRepository = reqHasTagRepository;
        this.userRepository = userRepository;
    }


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
        request.setUser(userRepository.getOne(userId));
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
  /*
    public List<RequestGetter> getRequests() {
      
        List<Request> requests = requestRepository.findAll();

        List<RequestGetter> getter = new ArrayList<RequestGetter>();
        RequestGetter rg = new RequestGetter(); 
        int size = requests.size();
        Request tempRq = null;
        for(int i =0; i < size; ++i) {
            tempRq = requests.get(i);
            rg.setRequest(tempRq);
            User user = tempRq.getUser();
            if(user == null) {
                System.out.println("getRequests 여기서 유저를 못찾음");
                return null;
            }
            rg.setUserId(user.getId());
            rg.setUserName(user.getUserName());

            

            rg.getTags().add();
            getter.add(rg);
        }
           
           
        return getter;
       
    }s
    */
    public int testGet(){
        TestId ti = new TestId();
        ti.setId(1L);
        ti.setTitle("1234");
        return 1;
    }
   
 
    public String hello() {
        
        return "!1231254";
    }
}