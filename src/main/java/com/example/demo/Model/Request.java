package com.example.demo.Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import lombok.Data;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Data

@NoArgsConstructor
@Getter
@Setter
@Entity(name = "request")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // mysql에 자동증가 기능있는데 타입이 위임이므로 걔로 위임
    @Column(name = "requestId", nullable = true, unique = true)
    private Long requestId;
    
    @Column(name = "category", nullable = false)
    private String category;
    
    @Column(name = "detail", nullable = false)
    private String detail;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "uploadAt", nullable = false)
    private Date uploadAt;
    
    @Column(name = "deadline", nullable = false)
    private Long deadline;

    @Temporal(TemporalType.DATE)
    @Column(name = "hopeDate", nullable = false)
    private Date hopeDate;
    @Column(name = "user_userId", nullable = false)
    private Long user_userId;
    @Column(name = "state", nullable = false)
    private String state;


    // 다대다를 표현하기위해서 씀
    @OneToMany(mappedBy = "Request")
    private List<ReqHasTag> Request_Has_Tag = new ArrayList<>();
}