package com.example.demo.repository.master;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.example.demo.DTO.RequestDTO;
import com.example.demo.entity.Request;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
    "where r.category = :category " + 
    "and r.state = '요청 진행중' "  +
    "limit :start, :size", nativeQuery = true)
    public List<Map<String, Object>> getRequestsPaged(int start, int size, String category);

    @Query(value = 
    "select r.*, u.user_name " +  
    "from request r " + 
    "inner join user u on u.user_id = r.user_id " +
    "where r.state = '요청 진행중' " +
    "limit :start, :size", nativeQuery = true)
    public List<Map<String, Object>> getAllPaged(int start, int size);

    @Query(value = 
    "select r.*, u.user_name " +  
    "from user u  " + 
    "inner join request r on u.user_id = r.user_id "+
    "where r.user_id = :Id "
    , nativeQuery = true)
    public List<Map<String,Object>> getRequestByUserId(Long Id);

    @Query(value = 
    "select r.*, u.user_name " +  
    "from user u  " + 
    "inner join request r on u.user_id = r.user_id "+
    "where r.request_id = :Id "
    , nativeQuery = true)
    public Map<String,Object> getRequestByRequestId(Long Id);
    //public List<RequestDTO> findByUserId(@Param(value = "user_id") Long userId);
    public List<RequestDTO> findBy(Sort sort);
    public List<RequestDTO> findByCategory(String category, Pageable page);
    //public List<RequestDTO> findAllByUserId(long userId, Pageable page);
    public int countByCategoryAndState(String category, String State);
    public int countByState(String state);
    

}