package com.anabol.onlineshop.service;

import com.anabol.onlineshop.entity.User;

import java.util.List;

public interface UserService {
    List<User> findAll();

    User findById(int id);

    void insert(User user);

    void deleteById(int id);

    void update(User user);
}
