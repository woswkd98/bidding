package com.example.demo;

import java.util.List;

public interface CRUDInterface<T> {
    public T insert(T t);
    public T update(T t);
    public List<T> selectAll();
    public void deleteByKey(Long Key);
    public T selectByKey(Long key);
}