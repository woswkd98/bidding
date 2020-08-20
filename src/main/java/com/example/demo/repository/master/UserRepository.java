package com.example.demo.repository.master;

import com.example.demo.Model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    public User findByUserEmail(String userEmail);
}