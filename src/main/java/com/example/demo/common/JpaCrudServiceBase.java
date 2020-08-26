package com.example.demo.common;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public class JpaCrudServiceBase<T , R extends JpaRepository<T, Long>> {
    
    protected final R repository;

    public JpaCrudServiceBase(R repository) {
        this.repository = repository;
    }

    public T insert(T t) {
       return repository.save(t);
    }

    public T getOne(long id) {
        return this.repository.getOne(id);
    }

    public Optional<T> findById(long id) {
        return this.repository.findById(id);
    }   
    
    public List<T> findAll() {
        return this.repository.findAll();
    }

    public void deleteById(Long id) {
        this.repository.deleteById(id);
    } 

    
    

    /*
    public T insert(T t) {

        return t;

    }



    public T update(T t);
    public List<T> selectAll();
    public void deleteByKey(Long Key);
    public T findByKey(Long key);
    public T getBykey(long key);*/

}