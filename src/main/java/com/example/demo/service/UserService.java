package com.example.demo.service;

import java.util.List;

import com.example.demo.CRUDInterface;
import com.example.demo.Model.User;
import com.example.demo.repository.master.*;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements CRUDInterface<User> {

    
    private final UserRepository userRepository;

    @Override
    public int insert(User t) {
        
        return 0;
    }

    @Override
    public int update(User t) {

        return 0;
    }

    @Override
    public List<User> selectAll() {

        return null;
    }

    @Override
    public int deleteByKey(Long Key) {

        return 0;
    }

    @Override
    public User selectByKey(Long key) {
        
        return null;
    }

}