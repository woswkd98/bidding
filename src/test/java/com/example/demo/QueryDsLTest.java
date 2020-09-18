package com.example.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.example.demo.DTO.RequestDTO;
import com.example.demo.entity.QReqHasTag;
import com.example.demo.entity.QRequest;
import com.example.demo.entity.QTag;
import com.example.demo.entity.QUser;

import com.example.demo.entity.User;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest

@RequiredArgsConstructor
@Slf4j
public class QueryDsLTest {
    
    @Autowired
    private  JPAQueryFactory jpaQueryFactory;
    
    private OrderSpecifier<?> getOrder(Order order, String name) {
       
        Path<Object> path = Expressions.path(Object.class, QRequest.request, name);
        return new OrderSpecifier(order, path);
    }
    @Test
    public void test() {
        
        QUser user = QUser.user; 
        QRequest request = QRequest.request;
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
        List<RequestDTO> list = jpaQueryFactory.select(
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

            
            .offset((long)0)
            .limit(6)
            .orderBy(getOrder(Order.ASC, "deadline"))
       
            .fetch();
            
        list.forEach(a -> {
            
            System.out.println("1111111111111111111111111111111111111111111111111111111111111111111");
            System.out.println(a.getCategory());

            System.out.println("1111111111111111111111111111111111111111111111111111111111111111111");
        });
       

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
        QTag tag = QTag.tag;
        List<String> tags = new ArrayList<>();
        tags.add("bb");
        //tags.add("aa");
        List<RequestDTO> list = jpaQueryFactory.select(
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
            .innerJoin(request.request_Has_Tag,rht)
            .innerJoin(rht.tag, tag)
            .where(
                request.state.eq("요청 진행중")
                .and(tag.context.in(tags))
            )
            .offset((long)0)
            .limit(6)
            .orderBy(getOrder(Order.ASC, "deadline"))
            .fetch();
        list.forEach(a -> {
            
            System.out.println("1111111111111111111111111111111111111111111111111111111111111111111");
            System.out.println(a.getRequest_id());
            System.out.println(a.getCategory());
            System.out.println(a.getDetail());
            
            System.out.println("1111111111111111111111111111111111111111111111111111111111111111111");
        });
       

    }
}
