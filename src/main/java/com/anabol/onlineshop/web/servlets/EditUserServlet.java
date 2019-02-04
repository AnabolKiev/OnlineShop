package com.anabol.onlineshop.web.servlets;

import com.anabol.onlineshop.entity.User;
import com.anabol.onlineshop.service.UserService;
import com.anabol.onlineshop.web.templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class EditUserServlet extends HttpServlet {
    private UserService userService;

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        User user = userService.findById(id);
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("user", user);

        response.getWriter().println(PageGenerator.instance().getPage("edit.html", pageVariables));
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        User user = new User();
        user.setId(Integer.parseInt(request.getParameter("id")));
        user.setFirstName(request.getParameter("firstName"));
        user.setLastName(request.getParameter("lastName"));
        user.setSalary(Double.valueOf(request.getParameter("salary")));
        user.setDateOfBirth(LocalDate.parse(request.getParameter("dateOfBirth")));
        userService.update(user);

        response.sendRedirect(request.getContextPath() + "/users");
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

}