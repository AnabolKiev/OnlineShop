package com.anabol.onlineshop.web.servlets;

import com.anabol.onlineshop.entity.User;
import com.anabol.onlineshop.entity.UserRole;
import com.anabol.onlineshop.service.UserService;
import com.anabol.onlineshop.web.templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class LoginServlet extends HttpServlet {
    private UserService userService;
    private List<String> tokens;

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(PageGenerator.instance().getPage("login.html", null));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        User user = userService.getByName(login);
        if (password.equals(user.getPassword())) {  // Authentification
            if (UserRole.getByName(user.getRole()) == UserRole.ADMIN) { // Authorization as Admin
                String token = UUID.randomUUID().toString();
                tokens.add(token);
                Cookie cookie = new Cookie("user-token", token);
                response.addCookie(cookie);
                response.sendRedirect("/products");
            }
        }

        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("message", "Entered credentials are wrong");
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().println(PageGenerator.instance().getPage("login.html", pageVariables));
    }

    public LoginServlet(UserService userService, List<String> tokens) {
        this.userService = userService;
        this.tokens = tokens;
    }
}
