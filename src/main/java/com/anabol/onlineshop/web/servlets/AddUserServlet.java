//package com.anabol.onlineshop.web.servlets;
//
//import com.anabol.onlineshop.entity.User;
//import com.anabol.onlineshop.service.UserService;
//import com.anabol.onlineshop.web.templater.PageGenerator;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.time.LocalDate;
//
//public class AddUserServlet extends HttpServlet {
//    private UserService userService;
//
//    public void doGet(HttpServletRequest request,
//                      HttpServletResponse response) throws ServletException, IOException {
//        response.getWriter().println(PageGenerator.instance().getPage("add.html", null));
//        response.setContentType("text/html;charset=utf-8");
//        response.setStatus(HttpServletResponse.SC_OK);
//    }
//
//    public void doPost(HttpServletRequest request,
//                       HttpServletResponse response) throws ServletException, IOException {
//        User user = new User();
//        user.setFirstName(request.getParameter("firstName"));
//        user.setLastName(request.getParameter("lastName"));
//        user.setSalary(Double.valueOf(request.getParameter("salary")));
//        user.setDateOfBirth(LocalDate.parse(request.getParameter("dateOfBirth")));
//        userService.insert(user);
//
//        response.sendRedirect(request.getContextPath() + "/users");
//    }
//
//    public void setUserService(UserService userService) {
//        this.userService = userService;
//    }
//
//}
