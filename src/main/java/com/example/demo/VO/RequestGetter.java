package com.example.demo.VO;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.Model.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestGetter { // 리퀘스트 저장받을 떄 형식
    private Request request;
    private Long userId;
    private String userName;
    private List<String> tags;
}