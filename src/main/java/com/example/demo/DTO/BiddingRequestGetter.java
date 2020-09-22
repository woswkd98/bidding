package com.example.demo.DTO;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class BiddingRequestGetter {
    private long bidding_id;
    private int price;
    private long request_id;
    private long seller_id;
    private String bid_state;
    private String user_email;
    private String user_name;
    private long user_id;
    private String detail;
    private String category;
    private long deadline;
    private String hope_date;
    private Date upload_at;
    private String request_state;
    private String bid_profile_url;
}
