package com.anabol.onlineshop.web.auth;

import com.anabol.onlineshop.entity.CartElement;
import com.anabol.onlineshop.entity.User;
import com.anabol.onlineshop.entity.UserRole;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
public class Session {

    private String token;
    private User user;
    private UserRole userRole;
    private LocalDateTime expireDate;
    private Map<Integer, CartElement> cart;

}
