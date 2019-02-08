package com.anabol.onlineshop.web.servlets;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class LogoutServlet extends HttpServlet {
    public LogoutServlet(List<String> tokens) {
        this.tokens = tokens;
    }

    List<String> tokens;

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws IOException {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user-token")) {
                    tokens.remove(cookie.getValue());
                }
            }
        }
        response.sendRedirect("/login");
    }
}
