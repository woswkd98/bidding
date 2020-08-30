package com.example.demo.repository.master;

import java.util.List;

import com.example.demo.Model.Request;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
    

    public List<Request> findByCategoryOrderByDeadline(String category);

    @Query(value = "SELECT r.* FROM Request r " +
    "INNER JOIN req_has_tag rht " +
    "ON r.request_id = rht.reqHasTag_id " +
    "INNER JOIN tag t " +
    "ON t.tag_id = rht.reqHasTag_id " +
    "WHERE t.context = :tag", nativeQuery = true)
    public List<Request> getRequestByTag(String tag);
}