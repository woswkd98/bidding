package com.example.demo.DTO;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.example.demo.entity.Tag;
import com.example.demo.entity.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/*
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

*/

/*
   
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

*/

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class  RequestDTO {
    private Long request_id;
    private String category;
    private String detail;
    private Date upload_at;
    private Long deadline;
    private String hope_date;
    private String state;
    private String user_name;
    private Long user_id;

}


