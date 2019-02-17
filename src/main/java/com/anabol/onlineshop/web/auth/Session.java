package com.anabol.onlineshop.web.auth;

import com.anabol.onlineshop.entity.User;
import com.anabol.onlineshop.entity.UserRole;

import java.time.LocalDateTime;

public class Session {

    private String token;
    private User user;
    private UserRole userRole;
    private LocalDateTime expireDate;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public LocalDateTime getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(LocalDateTime expireDate) {
        this.expireDate = expireDate;
    }
}
