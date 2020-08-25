package com.example.demo.VO;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.Model.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RequestGetter { // 리퀘스트 저장받을 떄 형식
    private Long userId;
    private String userName;
    private Request request;
    private List<String> tags = new ArrayList<String>();
}