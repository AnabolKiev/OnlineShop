package com.anabol.onlineshop.service.impl;

import com.anabol.onlineshop.dao.UserDao;
import com.anabol.onlineshop.entity.User;
import com.anabol.onlineshop.service.UserService;

public class DefaultUserService implements UserService{
    private UserDao userDao;

    @Override
    public User getByName(String name) {
        return userDao.getByName(name);
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
