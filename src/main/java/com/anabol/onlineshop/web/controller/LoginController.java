package com.anabol.onlineshop.web.controller;

import com.anabol.onlineshop.service.SecurityService;
import com.anabol.onlineshop.web.auth.Session;
import com.anabol.onlineshop.web.templater.PageGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
    public String loginPage(HttpServletResponse response) throws IOException {
        return "login";
    }

    @PostMapping(path = "/login")
    protected String login(@RequestParam String login, @RequestParam String password, ModelMap modelMap,
                           HttpServletResponse response) throws IOException {
        Session session = securityService.login(login, password);
        if (session != null) {
            Cookie cookie = new Cookie("user-token", session.getToken());
            cookie.setMaxAge((int) LocalDateTime.now().until(session.getExpireDate(), ChronoUnit.SECONDS));
            response.addCookie(cookie);
            return "redirect:/products";
        } else {
            modelMap.put("message", "Entered credentials are wrong");
            return "login";
        }
    }

    @GetMapping(path = "/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
        return "redirect:/login";
    }

    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }
}
