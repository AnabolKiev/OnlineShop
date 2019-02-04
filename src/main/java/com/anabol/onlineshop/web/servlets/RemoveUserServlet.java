package com.anabol.onlineshop.web.servlets;

import com.anabol.onlineshop.service.UserService;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RemoveUserServlet extends HttpServlet {
    private UserService userService;

    public void doPost(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        userService.deleteById(id);

        response.sendRedirect(request.getContextPath() + "/users");
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
