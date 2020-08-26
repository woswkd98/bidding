package com.example.demo.Model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "ChatMsg")
@Table(name = "ChatMsg")
public class ChatMsg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // mysql에 자동증가 기능있는데 타입이 위임이므로 걔로 위임
    @Column(name = "chatMsg_id", nullable = false, unique = true)
    private Long id;
    
    @Column(name = "room_id", nullable = false)
    private String roomId;

    @Column(name = "userEmail", nullable = false)
    private String userName;

    @Column(name = "context" , nullable = false)
    private String context;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "uploadAt" , nullable = false)
    private Date uploadAt;
    
    @Column(name = "state", nullable = false)
    private int state; 
}