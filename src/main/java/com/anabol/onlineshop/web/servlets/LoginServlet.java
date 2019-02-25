package com.anabol.onlineshop.web.servlets;

import com.anabol.onlineshop.service.SecurityService;
import com.anabol.onlineshop.web.auth.Session;
import com.anabol.onlineshop.web.templater.PageGenerator;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

public class LoginServlet extends HttpServlet {
    private SecurityService securityService;

    public LoginServlet() {
    }

    public LoginServlet(SecurityService securityService) {
        this.securityService = securityService;
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(PageGenerator.instance().getPage("login.html"));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        Session session = securityService.login(login, password);
        if (session != null) {
            Cookie cookie = new Cookie("user-token", session.getToken());
            cookie.setMaxAge((int) LocalDateTime.now().until(session.getExpireDate(), ChronoUnit.SECONDS));
            response.addCookie(cookie);
            response.sendRedirect("/products");
        } else {
            Map<String, Object> pageVariables = new HashMap<>();
            pageVariables.put("message", "Entered credentials are wrong");
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().println(PageGenerator.instance().getPage("login.html", pageVariables));
        }
    }

}
