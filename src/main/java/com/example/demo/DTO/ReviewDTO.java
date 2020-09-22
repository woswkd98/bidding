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
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {
    private Long userEmail;
    private String userName;
    private Date upload_at;
    private Long deadline;
    private String hope_date;
    private String state;
    private String user_name;
    private Long user_id;
}
