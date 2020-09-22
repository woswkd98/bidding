package com.example.demo;

import static org.mockito.ArgumentMatchers.booleanThat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.example.demo.DTO.RequestDTO;
import com.example.demo.entity.QReqHasTag;
import com.example.demo.entity.QRequest;
import com.example.demo.entity.QTag;
import com.example.demo.entity.QUser;
import com.example.demo.entity.Request;
import com.example.demo.entity.User;
import com.example.demo.repository.master.TagRepository;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.querydsl.core.types.dsl.BooleanExpression;

@SpringBootTest

@RequiredArgsConstructor
@Slf4j
public class QueryDsLTest {
    
    @Autowired
    private  JPAQueryFactory jpaQueryFactory;
    
    @Autowired
    private TagRepository tagRepository;
   
    private double calIdx(int count, int size) {
        System.out.println(Math.ceil((double) count / size));
        return Math.ceil((double) count / size);
    }

    private OrderSpecifier<?> getOrder(Order order, String name) {
       
        Path<Object> path = Expressions.path(Object.class, QRequest.request, name);
        return new OrderSpecifier(order, path);
    }

    @Test
    public void test2() {
        
       
        /*
            "select r.*, u.user_name " +  
    "from request r " + 
    "inner join user u on u.user_id = r.user_id " +
    "where r.state = '요청 진행중' " +
    "limit :start, :size", nativeQuery = true)

      private Long request_id;
    private String category;
    private String detail;
    private Date upload_at;
    private Long deadline;
    private String hope_date;
    private String state;
    private String user_name;
    private Long user_id;
        */
      
        QUser user = QUser.user;
        QRequest request = QRequest.request;
        QReqHasTag rht = QReqHasTag.reqHasTag;
        Map<String, Object> newMap = new HashMap<String, Object>();
        QTag tag = QTag.tag;
        Order order;
        boolean OrderPos = true;
        String orderName = "deadline"; 
        String category = "모든 요청";
        List<String> inputTag = new ArrayList<String>();
       // inputTag.add("리액트");
      //  inputTag.add("네이티브");   
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
   
        JPAQuery<RequestDTO> query = jpaQueryFactory.select(
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
        
        JPAQuery<Request> countQuery = jpaQueryFactory.selectFrom(request);
        
        if(!inputTag.isEmpty()) {
            System.out.println("------------");
            System.out.println("inputTag.size()");
            System.out.println(inputTag.size());
            System.out.println("------------");
            
            query
            .innerJoin(request.request_Has_Tag,rht)
            .innerJoin(rht.tag, tag).fetchJoin();
            predicate = tag.context.eq(inputTag.get(0));
         
            countQuery
                .innerJoin(request.request_Has_Tag,rht)
                .innerJoin(rht.tag, tag).fetchJoin();
            for(int i =1; i < inputTag.size(); ++i) {
                predicate.or(tag.context.eq(inputTag.get(i)));
            }

        }
   
            List<RequestDTO> list  = query
            .where(predicate)
            .offset(0)
            .limit(6)
            .orderBy(orderSpecifier)
            .fetchAll().fetch();
            System.out.println("------------");
            System.out.println("list.size()");
            System.out.println(list.size());

        newMap.put("requestList", list);
        List<List<String>> lists = new ArrayList<List<String>>();
        
        list.forEach(a -> {
            System.out.println("------------");
            System.out.println(a.getCategory());
            System.out.println(a.getState());
            System.out.println(a.getRequest_id());
            
            lists.add(tagRepository.getTagsByRequestId(a.getRequest_id()));
        });
        
        System.out.println(list.size());
   

        newMap.put("count", this.calIdx((int)countQuery.where(predicate).fetchCount(), 6));
        newMap.put("tags", lists);

    }
}
