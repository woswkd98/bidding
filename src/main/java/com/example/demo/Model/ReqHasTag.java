package com.example.demo.Model;

import java.io.Serializable;

import javax.persistence.*;

import lombok.Data;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

// 요청과 태그를 연결시켜주는 테이블 

@Data
@Getter
@Setter
@NoArgsConstructor
@Entity(name = "ReqHasTag")
@Table(name = "ReqHasTag")
public class ReqHasTag{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // mysql에 자동증가 기능있는데 타입이 위임이므로 걔로 위임
    @Column(name = "reqHasTag_id")
    private Long id ;

    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @ManyToOne
    @JoinColumn(name = "request_id")
    private Request request;
}