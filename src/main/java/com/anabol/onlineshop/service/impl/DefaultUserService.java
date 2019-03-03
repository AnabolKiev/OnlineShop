package com.anabol.onlineshop.service.impl;

import com.anabol.onlineshop.dao.UserDao;
import com.anabol.onlineshop.entity.User;
import com.anabol.onlineshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultUserService implements UserService{
    private final UserDao userDao;

    @Autowired
    public DefaultUserService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User getByName(String name) {
        return userDao.getByName(name);
    }

    @Override
    public void insert(User user) {
        userDao.insert(user);
    }

}
