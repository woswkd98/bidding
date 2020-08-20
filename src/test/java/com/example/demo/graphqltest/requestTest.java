package com.example.demo.graphqltest;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.stereotype.Component;

import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;


@Component
//추가
@GraphQLApi
public class requestTest {

	//{
	//	posts{
	//		id
	//  	title
	//	}
	//}
	
	
	@GraphQLQuery(name = "post")
	public String getPostById(Long id){
		return "test";
	}
	
	
	
}