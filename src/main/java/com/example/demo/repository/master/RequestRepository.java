package com.example.demo.repository.master;

import java.util.List;
import java.util.Map;

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
    "ON r.request_id = rht.request_id " +
    "INNER JOIN tag t " +
    "ON t.tag_id = rht.tag_id " +
    "WHERE t.context = :tag", nativeQuery = true)
    public List<Request> getRequestByTag(String tag);

    @Query(value = 
    "select r.*, u.user_name " +  
    "from request r " + 
    "inner join user u on u.user_id = r.user_id "+
    "limit :start, :size", nativeQuery = true)
    public List<Map<String, Object>> getRequestsPaged(int start, int size);

    @Query(value = 
    "select r.*, u.user_name " +  
    "from request r " + 
    "inner join user u on u.user_id = r.user_id "+
    "where r.user_id = :userId", nativeQuery = true)
    public  List<Map<String, Object>> getRequestById(long userId);
    
}