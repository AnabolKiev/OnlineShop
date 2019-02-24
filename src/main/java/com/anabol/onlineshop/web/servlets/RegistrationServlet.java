package com.anabol.onlineshop.web.servlets;

import com.anabol.onlineshop.service.SecurityService;
import com.anabol.onlineshop.web.ServiceLocator;
import com.anabol.onlineshop.web.templater.PageGenerator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RegistrationServlet extends HttpServlet {
    private SecurityService securityService = ServiceLocator.getService(SecurityService.class);

    public RegistrationServlet() {
    }

    public RegistrationServlet(SecurityService securityService) {
        this.securityService = securityService;
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(PageGenerator.instance().getPage("registration.html"));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");

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
}
