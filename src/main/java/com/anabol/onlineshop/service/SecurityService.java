package com.anabol.onlineshop.service;

import com.anabol.onlineshop.web.auth.Session;

public interface SecurityService {

    Session login(String name, String password);

    Session findByToken(String token);

    void removeByToken(String token);

}
