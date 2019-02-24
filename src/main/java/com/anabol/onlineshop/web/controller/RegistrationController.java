package com.anabol.onlineshop.web.controller;

import com.anabol.onlineshop.service.SecurityService;
import com.anabol.onlineshop.web.templater.PageGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(path = "/registration")
public class RegistrationController {
    @Autowired
    private SecurityService securityService;

    @GetMapping
    @ResponseBody
    public String registrationPage() throws IOException {
        return PageGenerator.instance().getPage("registration.html");
    }

    @PostMapping
    protected void registerUser(@RequestParam String login, @RequestParam String password, HttpServletResponse response) throws IOException {
        boolean isUserCreated = securityService.register(login, password);
        if (isUserCreated) {
            response.sendRedirect("/login");
        } else {
            Map<String, Object> pageVariables = new HashMap<>();
            pageVariables.put("message", "Entered login already exists");
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().println(PageGenerator.instance().getPage("registration.html", pageVariables));
        }
    }

    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }
}
