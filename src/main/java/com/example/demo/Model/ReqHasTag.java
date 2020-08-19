package com.example.demo.Model;
import javax.persistence.*;

import lombok.Data;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
@Data

@NoArgsConstructor
@Getter
@Setter
@Entity(name = "request_has_tag")
public class ReqHasTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // mysql에 자동증가 기능있는데 타입이 위임이므로 걔로 위임
    @Column(name = "rhtId")
    private Long rhtId ;

    @ManyToOne
    @JoinColumn(name = "tag_tagId")
    private Tag tag;

    @ManyToOne
    @JoinColumn(name = "request_requestId")
    private Request request;
}