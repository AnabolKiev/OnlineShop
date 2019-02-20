package com.anabol.onlineshop;

import com.anabol.onlineshop.dao.ProductDao;
import com.anabol.onlineshop.dao.UserDao;
import com.anabol.onlineshop.dao.jdbc.JdbcProductDao;
import com.anabol.onlineshop.dao.jdbc.JdbcUserDao;
import com.anabol.onlineshop.service.impl.DefaultProductService;
import com.anabol.onlineshop.service.impl.DefaultSecurityService;
import com.anabol.onlineshop.service.impl.DefaultUserService;
import com.anabol.onlineshop.web.filters.AdminRoleFilter;
import com.anabol.onlineshop.web.filters.UserRoleFilter;
import com.anabol.onlineshop.web.servlets.*;
import com.anabol.onlineshop.web.servlets.product.AddProductServlet;
import com.anabol.onlineshop.web.servlets.product.DeleteProductServlet;
import com.anabol.onlineshop.web.servlets.product.EditProductServlet;
import com.anabol.onlineshop.web.servlets.product.ShowProductServlet;
import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {

        // configuration
        // connection properties
        Properties jdbcProperties = new Properties();

        try (FileInputStream in = new FileInputStream("src/main/resources/jdbc.properties")) {
            jdbcProperties.load(in);
        }
        DataSource dataSource = createDataSource(jdbcProperties);

        // Wiring dao
        UserDao userDao = new JdbcUserDao(dataSource);
        ProductDao productDao = new JdbcProductDao(dataSource);

        // Wiring services
        DefaultUserService userService = new DefaultUserService();
        userService.setUserDao(userDao);

        DefaultProductService productService = new DefaultProductService();
        productService.setProductDao(productDao);

        DefaultSecurityService securityService = new DefaultSecurityService();
        securityService.setUserService(userService);

        // servlets mapping
//        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
//        ServletHolder productsServletHolder = new ServletHolder(new ShowProductServlet(productService, securityService));
//        context.addServlet(productsServletHolder, "/");
//        context.addServlet(productsServletHolder, "/products");
//        context.addServlet(new ServletHolder(new AddProductServlet(productService)), "/product/add");
//        context.addServlet(new ServletHolder(new EditProductServlet(productService)), "/product/edit/*");
//        context.addServlet(new ServletHolder(new DeleteProductServlet(productService)), "/product/delete/*");
//        context.addServlet(new ServletHolder(new LoginServlet(securityService)), "/login");
//        context.addServlet(new ServletHolder(new LogoutServlet(securityService)), "/logout");
//        context.addServlet(new ServletHolder(new RegistrationServlet(securityService)), "/registration");

        // filters
//        FilterHolder userFilterHolder = new FilterHolder(new UserRoleFilter(securityService));
//        context.addFilter(userFilterHolder, "/", EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD));
//        context.addFilter(userFilterHolder, "/products", EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD));
//        context.addFilter(new FilterHolder(new AdminRoleFilter(securityService)), "/product/*",
//                EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD));
//
//        Server server = new Server(8080);
//        server.setHandler(context);
//        server.start();
    }

    private static DataSource createDataSource(Properties properties) {
        MysqlConnectionPoolDataSource dataSource = new MysqlConnectionPoolDataSource();
        dataSource.setUrl(properties.getProperty("jdbc.url"));
        dataSource.setUser(properties.getProperty("jdbc.username"));
        dataSource.setPassword(properties.getProperty("jdbc.password"));
        return dataSource;
    }
}