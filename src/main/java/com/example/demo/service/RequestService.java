package com.example.demo.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.PostConstruct;

import com.example.demo.common.*;
import com.example.demo.Model.Request;
import com.example.demo.VO.RequestGetter;
import com.example.demo.columnMappingInterfaces.UserGetter;
import com.example.demo.repository.master.ReqHasTagRepository;
import com.example.demo.repository.master.RequestRepository;
import com.example.demo.repository.master.TagRepository;
import com.example.demo.repository.master.UserRepository;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.Config.GetTimeZone;
import com.example.demo.Model.*;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
@Component
@RequiredArgsConstructor
public class RequestService  {

    private final RequestRepository requestRepository;
    private final TagRepository tagRepository;
    private final ReqHasTagRepository reqHasTagRepository;
    private final UserRepository userRepository;
    private Sort sortByDeadLine;

    @PostConstruct
    public void init() {
        sortByDeadLine = Sort.by(Sort.Direction.ASC, "deadlines");
    }
    
    @Transactional
    public Request insert(RequestGetter requestGetter) {

        Request request = new Request();
        request.setCategory(requestGetter.getCategory());
        request.setDetail(requestGetter.getDetail());
        request.setHopeDate(GetTimeZone.StringToDateYMD(requestGetter.getHopeDate()));
        request.setUser(userRepository.findById(requestGetter.getUserId()).get());
        request.setDeadline(requestGetter.getDeadline());
        request.setUploadAt(new Date());
        request.setState(requestGetter.getState());
        request = requestRepository.save(request);

        List<String> tags = requestGetter.getTags();
        int size = tags.size();
        Tag tag = null;
        ReqHasTag reqHasTag = null;
        
        for(int i = 0 ; i < size; ++i) {
            
            tag = tagRepository.findByContext(tags.get(i));
            if(tag != null) {
                tag.setRequestCount(tag.getRequestCount() + 1);
                System.out.println("1235");
            }
            else {
                tag = new Tag();
                tag.setContext(tags.get(i));
                tag.setRequestCount(1);
            }
            reqHasTag = new ReqHasTag();
            reqHasTag.setRequest(request);
            reqHasTag.setTag(tag);
            tagRepository.save(tag);
            reqHasTagRepository.save(reqHasTag);
        }

        return request;
    }
    @Transactional
    public String delete(long id) {
        
        Optional<Request> ops = requestRepository.findById(id);
        if(!ops.isPresent())return "아이디 없음";
        Request request = ops.get();
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
        requestRepository.deleteById(id);
        return "성공";
    }

    public List<Request> getAll() {
        return requestRepository.findAll(sortByDeadLine);
    }

    public Request getOne(long id) {
        return requestRepository.getOne(id);
    }
    
    public List<Map<String, Object>> findById(long id) {
        return requestRepository.getRequestById(id);
    } 

    public List<Map<String, Object>> getRequestsPaged(int start, int size) {
        return requestRepository.getRequestsPaged(start, size); 
        
    }

    public List<Request> findByCategory(String category) {
        return requestRepository.findByCategoryOrderByDeadline(category);
    }

    public List<Request> findByTag(String tag) {
        return requestRepository.getRequestByTag(tag);
    }

    public List<Request> paging(int size, int page) {
        PageRequest pageReq = PageRequest.of(page, size, Sort.by(Direction.ASC,"Deadline"));
        return requestRepository.findAll(pageReq).getContent();
    }

    public String requestTimeOver(long requestId) {
        Request request = requestRepository.findById(requestId).get();
        if(request.getBidding().size() == 0) {
            request.setState("취소된 거래");
        }
        else request.setState("요청 시간 마감");

        requestRepository.save(request);
        return request.getState();
    }
    
}