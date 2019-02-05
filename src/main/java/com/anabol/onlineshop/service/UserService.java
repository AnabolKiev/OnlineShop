package com.anabol.onlineshop.service;

import com.anabol.onlineshop.entity.User;

public interface UserService {

    User getByName(String name);
}
