package com.anabol.onlineshop.service;

import com.anabol.onlineshop.web.auth.Session;

import java.security.NoSuchAlgorithmException;

public interface SecurityService {

    Session login(String name, String password);

    Session findByToken(String token);

    Session findByUserName(String login);

    void removeByToken(String token);

    boolean register(String login, String password);
}
