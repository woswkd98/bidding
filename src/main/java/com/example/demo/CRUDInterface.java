package com.example.demo;

import java.util.List;

public interface CRUDInterface<T> {
    public int insert(T t);
    public int update(T t);
    public List<T> selectAll();
    public int deleteByKey(Long Key);
    public T selectByKey(Long key);
}