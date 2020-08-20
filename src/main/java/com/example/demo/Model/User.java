
package com.example.demo.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Data
@Entity(name ="user")
@NoArgsConstructor
@Getter
@Setter
@Table(name = "user")
public class User  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // mysql에 자동증가 기능있는데 타입이 위임이므로 걔로 위임
    @Column(name = "user_id", nullable = false, unique = true)
    private Long id;
    
    @Column(name = "userPassword", nullable = false)
    private String userPassword;
    
    @Column(name = "userEmail", nullable = false)
    private String userEmail;
    
    @Column(name = "userName", nullable = false)
    private String userName;
    
    @Column(name = "phone", nullable = true)
    private String phone;
    
    @Column(name = "profileImage", nullable = true)
    private String profileImage;

    @Column(name = "state", nullable = false)
    private String state;

    @OneToMany(mappedBy = "user")
    private List<Bidding> bidding = new ArrayList<>();
}