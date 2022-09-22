package com.test_task.service;

import java.util.List;

public interface IService<T> {

    void save(T t);

    void deleteById(Long id);

    void edit(T t);

    List<T> findAll();

    T findById(Long id);
}
