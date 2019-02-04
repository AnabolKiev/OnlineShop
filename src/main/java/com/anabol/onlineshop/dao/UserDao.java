package com.anabol.onlineshop.dao;

import com.anabol.onlineshop.entity.User;

import java.util.List;

public interface UserDao {
    List<User> findAll();

    User findById(int id);

    void insert(User user);

    void deleteById(int id);

    void update(User user);
}
