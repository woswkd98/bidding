package com.example.demo.VO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@Entity
@ToString
public class TestId {
	
	@Id
	@GeneratedValue
	@GraphQLQuery(name = "id")
	private Long id;
	
	@GraphQLQuery(name = "title")
	private String title;
}
