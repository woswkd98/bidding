package com.example.demo;

import java.util.List;

import com.example.demo.DTO.RequestDTO;
import com.example.demo.repository.master.RequestRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;


@SpringBootTest
@Component
public class RequestTest {
    
    private RequestRepository requestRepository;
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(RequestTest.class);
    @Autowired
    public RequestTest(RequestRepository requestRepository) {
        this.requestRepository =requestRepository;
    }
  /*
    @Test
    public void interfaceMappingTest() {
        List<RequestDTO> dto = requestRepository.findAllBy(Sort.by(Direction.DESC, "category"));
        //List<RequestDTO> dto = requestRepository.findAll(RequestDTO.class);
        for(int i =0; i <dto.size(); ++i) {
           System.out.print(dto.get(i).getCategory());
        }
    }
  
    @Test
    public void interfaceMappingTestByCategorySortBy() {
        List<RequestDTO> dto = requestRepository.findAllBy(Sort.by(Direction.DESC, "category"));
        //List<RequestDTO> dto = requestRepository.findAll(RequestDTO.class);
        for(int i =0; i <dto.size(); ++i) {
           System.out.println(dto.get(i).getCategory());
           System.out.println(dto.get(i).getUserId());
           dto.get(i).toString();
        }
    }
*/
    
}
