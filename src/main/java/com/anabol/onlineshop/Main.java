package com.anabol.onlineshop;

import com.anabol.onlineshop.dao.UserDao;
import com.anabol.onlineshop.dao.jdbc.JdbcUserDao;
import com.anabol.onlineshop.service.impl.DefaultUserService;
import com.anabol.onlineshop.web.servlets.*;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class Main {
    public static void main(String[] args) throws Exception {
        // configuration
        UserDao userDao = new JdbcUserDao();
        DefaultUserService userService = new DefaultUserService();
        userService.setUserDao(userDao);

        //servlets
        ShowUsersServlet showUsersServlet = new ShowUsersServlet();
        showUsersServlet.setUserService(userService);

        AddUserServlet addUserServlet = new AddUserServlet();
        addUserServlet.setUserService(userService);

        EditUserServlet editUserServlet = new EditUserServlet();
        editUserServlet.setUserService(userService);

        RemoveUserServlet removeUserServlet = new RemoveUserServlet();
        removeUserServlet.setUserService(userService);

        // servlets mapping
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(showUsersServlet), "/users");
        context.addServlet(new ServletHolder(addUserServlet), "/users/add");
        context.addServlet(new ServletHolder(editUserServlet), "/users/*");
        context.addServlet(new ServletHolder(removeUserServlet), "/users/remove");

        Server server = new Server(8080);
        server.setHandler(context);
        server.start();
    }
}