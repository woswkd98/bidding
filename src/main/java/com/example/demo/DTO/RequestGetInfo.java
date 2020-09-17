package com.example.demo.DTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//int page, String category, String orderName, List<String> inputTag,
//boolean OrderPos 
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RequestGetInfo {
    private int page;
    private String category;
    private String orderName;
    private List<String> inputTag;
    private boolean OrderPos;

}
