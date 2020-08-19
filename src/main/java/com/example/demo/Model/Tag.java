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
@Entity(name = "tag")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // mysql에 자동증가 기능있는데 타입이 위임이므로 걔로 위임
    @Column(name = "tagId", nullable = false, unique = true)
    private Long tagId;
    
    @Column(name = "context", nullable = false)
    private String context;
    
    @Column(name = "bidCount", nullable = false)
    private int bidCount;
    
    @Column(name = "requestCount", nullable = false)
    private int requestCount;

    // 다대다를 표현하기위해서 씀
    @OneToMany(mappedBy = "Tag")
    private List<ReqHasTag> Request_Has_Tag = new ArrayList<>();
}