package com.anabol.onlineshop;

import com.anabol.onlineshop.dao.UserDao;
import com.anabol.onlineshop.dao.jdbc.JdbcUserDao;
import com.anabol.onlineshop.dao.jdbc.connection.DBConnection;
import com.anabol.onlineshop.service.impl.DefaultUserService;
import com.anabol.onlineshop.web.servlets.*;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.io.FileInputStream;
import java.util.Properties;

public class Main {
    public static void main(String[] args) throws Exception {

        // configuration
        // connection properties
        Properties jdbcProperties = new Properties();
        try (FileInputStream in = new FileInputStream("src/main/jdbc.properties")) {
            jdbcProperties.load(in);
        }
        String driver = jdbcProperties.getProperty("jdbc.driver");
        String url = jdbcProperties.getProperty("jdbc.url");
        String username = jdbcProperties.getProperty("jdbc.username");
        String password = jdbcProperties.getProperty("jdbc.password");
        DBConnection dbConnection = new DBConnection(driver, url, username, password);

        // Wiring dao and service
        UserDao userDao = new JdbcUserDao(dbConnection);
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