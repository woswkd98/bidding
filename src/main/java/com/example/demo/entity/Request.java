package com.example.demo.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.ColumnDefault;

import lombok.Data;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data

@Getter
@Setter
@Entity(name = "request")
@Table(name = "request")
public class Request  {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // mysql에 자동증가 기능있는데 타입이 위임이므로 걔로 위임
    @Column(name = "request_id", nullable = true, unique = true)
    private Long id;
    
    @Column(name = "category", nullable = false)
    private String category;
    
    @Column(name = "detail", nullable = false)
    private String detail;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "uploadAt", nullable = false)
    private Date uploadAt;
    
    @Column(name = "deadline", nullable = false)
    private Long deadline;


    @Column(name = "hopeDate", nullable = true)
    private String hopeDate;

    @Column(name = "state", nullable = false)
    private String state;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // 유저도 여러개의 입찰을 가질 수 있고 요청도 여러개의 입찰을 가질 수 있으므로 여기도 다대다 
    @OneToMany(mappedBy = "request")
    private List<Bidding> bidding = new ArrayList<>();

    // 다대다를 표현하기위해서 씀
    @OneToMany(mappedBy = "request")
    private List<ReqHasTag> request_Has_Tag = new ArrayList<>();

   

}