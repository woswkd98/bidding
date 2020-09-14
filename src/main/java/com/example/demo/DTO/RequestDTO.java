package com.example.demo.DTO;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.example.demo.Model.Tag;
import com.example.demo.Model.User;

import lombok.NoArgsConstructor;
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


public interface  RequestDTO {
    public  String getCategory();
    public String getDetail();
    public Date getUploadAt();
    public Long getId();
    public Long getDeadline();
    public String getHopeDate();
    public String getState();
    public User getUser();
    default Map<String, Object> requestSender() {
        Map<String, Object> map = new HashMap<String,Object>();
        map.put("category", this.getCategory());
        map.put("detail", this.getDetail());
        map.put("hope_date", this.getHopeDate());
        map.put("upload_at", this.getUploadAt());
        map.put("state", this.getState());
        map.put("request_id", this.getId());
      
        
        return map;
    }

    
}


