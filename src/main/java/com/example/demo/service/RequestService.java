package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import com.example.demo.common.*;
import com.example.demo.Model.Request;
import com.example.demo.VO.RequestGetter;
import com.example.demo.repository.master.ReqHasTagRepository;
import com.example.demo.repository.master.RequestRepository;
import com.example.demo.repository.master.TagRepository;

import org.springframework.stereotype.Component;
import com.example.demo.Model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
@Component
@RequiredArgsConstructor
public class RequestService  {

    private final RequestRepository requestRepository;
    private final TagRepository tagRepository;
    private final ReqHasTagRepository reqHasTagRepository;
    private Sort sortByDeadLine;

    @PostConstruct
    public void init() {
        sortByDeadLine = Sort.by(Sort.Direction.ASC, "deadlines");
    }
    
    public Request insert(RequestGetter requestGetter) {

        Request request = requestRepository.save(requestGetter.getRequest());



        List<String> tags = requestGetter.getTags();
        int size = tags.size();
        Tag tag = null;
        ReqHasTag reqHasTag = null;
        
        for(int i = 0 ; i < size; ++i) {
            
            tag = tagRepository.findByContext(tags.get(i));
            if(tag != null) {
                tag.setRequestCount(tag.getRequestCount() + 1);
            }
            else {
                tag = new Tag();
                tag.setContext(tags.get(i));
            }

            tag = new Tag();
            tag.setContext(tags.get(i));
            reqHasTag = new ReqHasTag();
            reqHasTag.setRequest(request);
            reqHasTag.setTag(tag);
            tagRepository.save(tag);
            reqHasTagRepository.save(reqHasTag);
        }

        return request;
    }

    public void delete(long id) {
        
        Request request = requestRepository.findById(id).get();
        List<ReqHasTag> rhts = request.getRequest_Has_Tag(); 
        Tag tempTag = null;
        ReqHasTag rht = null;
        for(int i =0; i < rhts.size(); ++i) {
            rht = rhts.get(i);
            tempTag = rht.getTag();
            if(tempTag.getRequestCount() > 1) {
                tempTag.setRequestCount(tempTag.getRequestCount() - 1);
            }
            else {
                tagRepository.delete(tempTag);
            }
            reqHasTagRepository.delete(rht);
        }
    }

    public List<Request> getAll() {
        return requestRepository.findAll(sortByDeadLine);
    }

    public Request getOne(long id) {
        return requestRepository.getOne(id);
    }
    
    public Optional<Request> findAll(long id) {
        return requestRepository.findById(id);
    } 
    public List<Request> findByCategory(String category) {
        return requestRepository.findByCategoryOrderByDeadline(category);
    }

    public List<Request> findByTag(String tag) {
        return requestRepository.getRequestByTag(tag);
    }

    
}