package com.anabol.onlineshop.web.controller;

import com.anabol.onlineshop.service.SecurityService;
import com.anabol.onlineshop.web.templater.PageGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
    public String registrationPage() throws IOException {
        return "registration";
    }

    @PostMapping
    protected String registerUser(@RequestParam String login, @RequestParam String password, ModelMap modelMap) throws IOException {
        boolean isUserCreated = securityService.register(login, password);
        if (isUserCreated) {
            return "redirect:/login";
        } else {
            modelMap.put("message", "Entered login already exists");
            return "registration";
        }
    }

    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }
}
