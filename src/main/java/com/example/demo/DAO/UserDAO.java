package com.example.demo.DAO;

import java.util.List;

import com.example.demo.CRUDInterface;
import com.example.demo.Model.User;
import com.example.demo.repository.master.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;

public class UserDAO implements CRUDInterface<User> {

    private final UserRepository userRepository;

    @Autowired
    public UserDAO(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

	@Override
	public User insert(User t) {
		return userRepository.saveAndFlush(t);
	}

	@Override
	public User update(User t) {
		return userRepository.save(t);
	}

	@Override
	public List<User> selectAll() {
		return userRepository.findAll();
	}

	@Override
	public void deleteByKey(Long Key) {
		userRepository.deleteById(Key);
	}

	@Override
	public User selectByKey(Long key) {
		return userRepository.findById(key).get();
	}

    
}