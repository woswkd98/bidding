package com.example.demo.repository.master;

import java.util.List;
import java.util.Optional;

import com.example.demo.entity.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository  extends JpaRepository<Tag, Long> {
   
   @Query(value = "SELECT t.context FROM request r " +
   "INNER JOIN req_has_tag rht " +
   "ON r.request_id = rht.request_id " +
   "INNER JOIN tag t " +
   "ON t.tag_id = rht.tag_id " +
   "WHERE r.request_id = :requestId", nativeQuery = true)
   List<String> getTagsByRequestId(long requestId);
   Tag findByContext(String context);
}