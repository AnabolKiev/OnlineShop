package com.anabol.onlineshop;

import com.anabol.onlineshop.dao.ProductDao;
import com.anabol.onlineshop.dao.UserDao;
import com.anabol.onlineshop.dao.jdbc.JdbcProductDao;
import com.anabol.onlineshop.dao.jdbc.JdbcUserDao;
import com.anabol.onlineshop.dao.jdbc.connection.DBConnection;
import com.anabol.onlineshop.service.impl.DefaultProductService;
import com.anabol.onlineshop.service.impl.DefaultUserService;
import com.anabol.onlineshop.web.servlets.*;
import com.anabol.onlineshop.web.servlets.product.AddProductServlet;
import com.anabol.onlineshop.web.servlets.product.DeleteProductServlet;
import com.anabol.onlineshop.web.servlets.product.EditProductServlet;
import com.anabol.onlineshop.web.servlets.product.ShowProductServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
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

        List<String> tokens = new ArrayList<>();

        // Wiring dao and service
        UserDao userDao = new JdbcUserDao(dbConnection);
        DefaultUserService userService = new DefaultUserService();
        userService.setUserDao(userDao);

        ProductDao productDao = new JdbcProductDao(dbConnection);
        DefaultProductService productService = new DefaultProductService();
        productService.setProductDao(productDao);

        // servlets mapping
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(new ShowProductServlet(productService)), "/products");
        context.addServlet(new ServletHolder(new AddProductServlet(productService, tokens)), "/product/add");
        context.addServlet(new ServletHolder(new EditProductServlet(productService, tokens)), "/product/edit/*");
        context.addServlet(new ServletHolder(new DeleteProductServlet(productService, tokens)), "/product/delete/*");
        context.addServlet(new ServletHolder(new LoginServlet(userService, tokens)), "/login");
        context.addServlet(new ServletHolder(new LogoutServlet(tokens)), "/logout");

        Server server = new Server(8080);
        server.setHandler(context);
        server.start();
    }
}