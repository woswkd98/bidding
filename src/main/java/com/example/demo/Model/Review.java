package com.example.demo.Model;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;
import lombok.Data;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Data
@NoArgsConstructor
@Getter
@Setter
@Entity(name ="review")
@Table(name = "review")
public class Review  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // mysql에 자동증가 기능있는데 타입이 위임이므로 걔로 위임
    @Column(name = "review_id", nullable = false, unique = true)
    private Long id;

    @Column(name = "grade", nullable = true)
    private float grade;

    @Column(name = "context", nullable = false)
    private String context;
    
    @Column(name = "uploadAt", nullable = false)
    private Date uploadAt;


    @ManyToOne
    @JoinColumn(name = "seller_id")
    private Seller seller;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}