package com.example.demo.VO;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChattingRoom implements Serializable {

    private static final long serialVersionUID = 2L;
    private String room_id;
    private long request_id;
    private long seller;
    private long buyer;

}