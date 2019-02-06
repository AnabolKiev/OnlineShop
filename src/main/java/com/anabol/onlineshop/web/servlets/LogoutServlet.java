package com.anabol.onlineshop.web.servlets;

import com.anabol.onlineshop.web.auth.TokenUtils;

import javax.servlet.ServletException;
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
                      HttpServletResponse response) throws ServletException, IOException {
        TokenUtils.logout(request, tokens);
        response.sendRedirect("/login");
    }
}
