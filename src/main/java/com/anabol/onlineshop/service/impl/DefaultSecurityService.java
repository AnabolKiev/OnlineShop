package com.anabol.onlineshop.service.impl;

import com.anabol.onlineshop.entity.User;
import com.anabol.onlineshop.entity.UserRole;
import com.anabol.onlineshop.service.SecurityService;
import com.anabol.onlineshop.service.UserService;
import com.anabol.onlineshop.web.auth.Session;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class DefaultSecurityService implements SecurityService {
    private static final int SESSION_LENGTH_HOURS = 12;

    private List<Session> sessions = Collections.synchronizedList(new ArrayList<>());

    private UserService userService;

    @Override
    public Session login(String login, String password) {
        User user = userService.getByName(login);
        if (user != null) {
            if (user.getPassword().equals(password)) {  // Authentication
                if (UserRole.getByName(user.getRole()) == UserRole.ADMIN) { // Authorization as Admin
                    Session session = new Session();
                    String token = UUID.randomUUID().toString();
                    session.setToken(token);
                    session.setUser(user);
                    session.setExpireDate(LocalDateTime.now().plusHours(SESSION_LENGTH_HOURS));
                    sessions.add(session);
                    return session;
                }
            }
        }
        return null;
    }

    @Override
    public Session findByToken(String token) {
        for (Session session : sessions) {
            if (session.getToken().equals(token)) {
                return session;
            }
        }
        return null;
    }

    @Override
    public void removeByToken(String token) {
        Session session = findByToken(token);
        if (session != null) {
            sessions.remove(session);
        }
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
