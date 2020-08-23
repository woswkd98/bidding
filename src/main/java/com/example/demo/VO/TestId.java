package com.example.demo.VO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
//@NoArgsConstructor
@Entity
@ToString
public class TestId {
	
	
	private Long id;

	private String title;
}
