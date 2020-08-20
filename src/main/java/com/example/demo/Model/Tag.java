package com.example.demo.Model;

import java.io.Serializable;
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
@Table(name = "tag")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // mysql에 자동증가 기능있는데 타입이 위임이므로 걔로 위임
    @Column(name = "tag_id", nullable = false, unique = true)
    private Long id;
    
    @Column(name = "context", nullable = false)
    private String context;
    
    @Column(name = "bidCount", nullable = false)
    private int bidCount;
    
    @Column(name = "requestCount", nullable = false)
    private int requestCount;

    // 태그와 요청과의 관계가 다대다 
    @OneToMany(mappedBy = "tag")
    private List<ReqHasTag> Request_Has_Tag = new ArrayList<>();

}