package com.anabol.onlineshop.web.controller;

import com.anabol.onlineshop.service.SecurityService;
import com.anabol.onlineshop.web.auth.Session;
import com.anabol.onlineshop.web.templater.PageGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {
    @Autowired
    private SecurityService securityService;

    @GetMapping(path = "/login")
    public void loginPage(HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(PageGenerator.instance().getPage("login.html"));
    }

    @PostMapping(path = "/login")
    protected void login(@RequestParam String login, @RequestParam String password, HttpServletResponse response) throws IOException {
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

    @GetMapping(path = "/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user-token")) {
                    securityService.removeByToken(cookie.getValue());
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }
            }
        }
        response.sendRedirect("/login");
    }

    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }
}
