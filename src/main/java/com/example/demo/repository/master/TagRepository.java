package com.example.demo.repository.master;

import java.util.List;

import com.example.demo.Model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository  extends JpaRepository<Tag, Long> {
    /*
    @Query("SELECT t.context FROM request r" + 
    "INNER JOIN request_has_tag rhtt" +
    "ON r.requestId = rht.request_requestId" +
    "INNER JOIN tag t" +
    "ON t.tagId = rht.tag_tagId" +
    "WHERE r.requestId = :requestId")
    public List<String> getTagContextsByRequest(Long RequestId);*/
}