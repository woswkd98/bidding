package com.example.demo.VO;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*  
@Column(name = "userPassword", nullable = false)
private String userPassword;

@Column(name = "userEmail", nullable = false, unique = true)
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

@OneToOne
@JoinColumn(name ="user_id")
private Images images;
*/
@Data

public class UserVO {
    private String userPassword;
    private String userEmail;
    private String userName;
    private String phone;
    private MultipartFile file;
}