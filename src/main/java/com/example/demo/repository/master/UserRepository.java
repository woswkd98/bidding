package com.example.demo.repository.master;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.example.demo.DTO.UserDTO;
import com.example.demo.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    public User findByUserEmail(String userEmail);

    @Query(value = "select user_id, user_name, user_email from user", nativeQuery = true)
    public List<Map<String, Object>> getAll();

    @Query(value = "select user_id, user_name, user_email from user where user_email = :email", nativeQuery = true)
    public List<Map<String,Object>> getUserByEmail(String email);

    public UserDTO findAllById(Long id);
}