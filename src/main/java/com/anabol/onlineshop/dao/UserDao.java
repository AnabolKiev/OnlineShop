package com.anabol.onlineshop.dao;

import com.anabol.onlineshop.entity.User;

public interface UserDao {

    User getByName(String name);

    void insert(User user);
}
