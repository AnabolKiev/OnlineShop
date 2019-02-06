package com.anabol.onlineshop.web.auth;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class TokenUtils {

    public static boolean isTokenValid(HttpServletRequest request, List<String> tokens) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user-token")) {
                    if (tokens.contains(cookie.getValue())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static void logout(HttpServletRequest request, List<String> tokens) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user-token")) {
                    tokens.remove(cookie.getValue());
                }
            }
        }
    }
}
