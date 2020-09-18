package com.example.demo.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.PostConstruct;

import com.example.demo.common.*;
import com.example.demo.entity.*;
import com.example.demo.VO.RequestGetter;


import com.example.demo.repository.master.ReqHasTagRepository;
import com.example.demo.repository.master.RequestRepository;
import com.example.demo.repository.master.SellerRepository;
import com.example.demo.repository.master.TagRepository;
import com.example.demo.repository.master.UserRepository;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.SimplePath;
import com.querydsl.jpa.JPAQueryBase;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.AbstractJPAQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.Config.GetTimeZone;
import com.example.demo.DTO.RequestDTO;
import com.example.demo.DTO.UserDTO;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

@Component
@RequiredArgsConstructor
public class RequestService {

    private final RequestRepository requestRepository;
    private final TagRepository tagRepository;
    private final ReqHasTagRepository reqHasTagRepository;
    private final UserRepository userRepository;
    private final JPAQueryFactory factory;
    private Sort sortByDeadLine;
    private OrderSpecifier orderbyRequest;

    private final int size = 6;

    private int calOffset(int page, int size) {
        return (page - 1) * size;
    }

    private double calIdx(int count, int size) {
        System.out.println(Math.ceil((double) count / size));
        return Math.ceil((double) count / size);
    }

    private OrderSpecifier<?> getOrder(Order order, String name) {

        Path<Object> path = Expressions.path(Object.class, QRequest.request, name);
        return new OrderSpecifier(order, path);
    }

    @PostConstruct
    public void init() {

        sortByDeadLine = Sort.by(Sort.Direction.ASC, "deadlines");
    }

    @Transactional
    public Request insert(RequestGetter requestGetter) {

        Request request = new Request();
        request.setCategory(requestGetter.getCategory());
        request.setDetail(requestGetter.getDetail());

        request.setHopeDate(requestGetter.getHopeDate());
        request.setUser(userRepository.findById(requestGetter.getUserId()).get());
        request.setDeadline(requestGetter.getDeadline());
        request.setUploadAt(new Date());
        request.setState(requestGetter.getState());
        request = requestRepository.save(request);

        List<String> tags = requestGetter.getTags();
        int size = tags.size();
        Tag tag = null;
        ReqHasTag reqHasTag = null;

        for (int i = 0; i < size; ++i) {

            tag = tagRepository.findByContext(tags.get(i));
            if (tag != null) {
                tag.setRequestCount(tag.getRequestCount() + 1);
                System.out.println("1235");
            } else {
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
        if (!ops.isPresent())
            return "아이디 없음";
        Request request = ops.get();
        List<ReqHasTag> rhts = request.getRequest_Has_Tag();
        Tag tempTag = null;
        ReqHasTag rht = null;
        for (int i = 0; i < rhts.size(); ++i) {
            rht = rhts.get(i);
            tempTag = rht.getTag();
            if (tempTag.getRequestCount() > 1) {
                tempTag.setRequestCount(tempTag.getRequestCount() - 1);
            } else {
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

    public Map<String, Object> getRequestByUserId(long id) {

        List<Map<String, Object>> list = requestRepository.getRequestByUserId(id);
        Map<String, Object> newMap = new HashMap<String, Object>();
        newMap.put("requestList", list);
        List<List<String>> lists = new ArrayList<List<String>>();
        for (int i = 0; i < list.size(); ++i) {
            String strID = list.get(i).get("request_id").toString();
            lists.add(tagRepository.getTagsByRequestId(Long.valueOf(strID)));
        }
        newMap.put("tags", lists);

        return newMap;
    }

    public Map<String, Object> getRequestById(long id) {
        Map<String, Object> list = requestRepository.getRequestByRequestId(id);
        Map<String, Object> newMap = new HashMap<String, Object>();
        newMap.put("requestList", list);
        String strID = list.get("request_id").toString();

        newMap.put("tags", tagRepository.getTagsByRequestId(Long.valueOf(strID)));

        return newMap;
    }

    public Map<String, Object> getRequestsPaged(int start, String category, String orderName) {

        QUser user = QUser.user;
        QRequest request = QRequest.request;
        QReqHasTag rht = QReqHasTag.reqHasTag;
        Map<String, Object> newMap = new HashMap<>();
        QTag tag = QTag.tag;
        List<RequestDTO> list = factory
                .select(Projections.constructor(RequestDTO.class, request.id, request.category, request.detail,
                        request.uploadAt, request.deadline, request.hopeDate, request.state, request.user.userName,
                        request.user.id))
                .from(request).innerJoin(request.user, user).where(request.state.eq("요청 진행중"))
                .offset((long) this.calOffset(start, size)).limit(size).orderBy(getOrder(Order.ASC, orderName))

                .fetch();
        newMap.put("requestList", list);
        List<List<String>> lists = new ArrayList<List<String>>();

        list.forEach(a -> {
            lists.add(tagRepository.getTagsByRequestId(a.getRequest_id()));
        });

        newMap.put("count", calIdx(requestRepository.countByCategoryAndState(category, "요청 진행중"), size));
        newMap.put("tags", lists);
        return newMap;
    }

    public Map<String, Object> getRequestByTag(int start, String category, String orderName, List<String> inputTag,
            boolean OrderPos) {
  
        QUser user = QUser.user;
        QRequest request = QRequest.request;
        QReqHasTag rht = QReqHasTag.reqHasTag;
        Map<String, Object> newMap = new HashMap<String, Object>();
        QTag tag = QTag.tag;
        Order order;
                
        if (OrderPos) {
            order = Order.ASC;
        } else
            order = Order.DESC;

        OrderSpecifier<?> orderSpecifier = null;

        if (orderName == null) {
            orderSpecifier = getOrder(order, "deadline");
        } else {
            orderSpecifier = getOrder(order, orderName);
        }
        System.out.println("1");
        BooleanExpression predicate = request.state.eq("요청 진행중");
        System.out.println(category);
        if (!category.equals("모든 요청")) {
            predicate = predicate.and(request.category.eq(category));
        }
   
        JPAQuery<RequestDTO> query = factory.select(
            Projections.constructor(
                RequestDTO.class, 
                request.id,
                request.category,
                request.detail,
                request.uploadAt,
                request.deadline,
                request.hopeDate,
                request.state,
                request.user.userName,
                request.user.id
            ))
            .from(request)
            .innerJoin(request.user, user);
        
        JPAQuery<Request> countQuery = factory.selectFrom(request);
        
        if(!inputTag.isEmpty()) {
            System.out.println("------------");
            System.out.println("inputTag.size()");
            System.out.println(inputTag.size());
            System.out.println("------------");
            predicate = predicate.and(tag.context.in(inputTag));
            query = query
            .innerJoin(request.request_Has_Tag,rht)
            .innerJoin(rht.tag, tag);

            countQuery = countQuery
                .innerJoin(request.request_Has_Tag,rht)
                .innerJoin(rht.tag, tag);
        }
        
        List<RequestDTO> list = query
            .where(predicate)
            .offset((long)this.calOffset(start, size))
            .limit(size)
            .orderBy(orderSpecifier)
            .fetch();

           

        newMap.put("requestList", list);
        List<List<String>> lists = new ArrayList<List<String>>();
        
        list.forEach(a -> {
            System.out.println("------------");
            System.out.println(a.getCategory());
            lists.add(tagRepository.getTagsByRequestId(a.getRequest_id()));
        });
        
   

        newMap.put("count", this.calIdx((int)countQuery.where(predicate).fetchCount(), size));
        newMap.put("tags", lists);
        return newMap; 
    }



    public  Map<String, Object> getAllPaged(int start,String orderName) {
        QUser user = QUser.user;
        QRequest request = QRequest.request;
        QReqHasTag rht = QReqHasTag.reqHasTag;
        Map<String,Object> newMap = new HashMap<>();
        QTag tag = QTag.tag;
        List<RequestDTO> list = factory.select(
            Projections.constructor(
            RequestDTO.class, 
            request.id,
            request.category,
            request.detail,
            request.uploadAt,
            request.deadline,
            request.hopeDate,
            request.state,
            request.user.userName,
            request.user.id
        ))
            .from(request)
            .innerJoin(request.user, user)
            .where(request.state.eq("요청 진행중"))
            .offset((long)this.calOffset(start, size))
            .limit(size)
            .orderBy(getOrder(Order.ASC, orderName))
            .fetch();
        newMap.put("requestList", list);
        List<List<String>> lists = new ArrayList<List<String>>();
        
        list.forEach(a -> {
            lists.add(tagRepository.getTagsByRequestId(a.getRequest_id()));
        });
         
        newMap.put("count", calIdx((int)requestRepository.countByState("요청 진행중"),size));
        newMap.put("tempCount", requestRepository.count());
        newMap.put("tags", lists);
        return newMap; 
        
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
        if(request.getBidding() == null) {
            request.setState("취소된 거래");
        }
        else request.setState("요청 시간 마감");

        requestRepository.save(request);
        return request.getState();
    }
    
}