package com.anabol.onlineshop.web;

import com.anabol.onlineshop.dao.ProductDao;
import com.anabol.onlineshop.dao.UserDao;
import com.anabol.onlineshop.dao.jdbc.JdbcProductDao;
import com.anabol.onlineshop.dao.jdbc.JdbcUserDao;
import com.anabol.onlineshop.service.ProductService;
import com.anabol.onlineshop.service.SecurityService;
import com.anabol.onlineshop.service.UserService;
import com.anabol.onlineshop.service.impl.DefaultProductService;
import com.anabol.onlineshop.service.impl.DefaultSecurityService;
import com.anabol.onlineshop.service.impl.DefaultUserService;
import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ServiceLocator {

    private static final Map<Class<?>, Object> SERVICES = new HashMap<>();

    static {
        // configuration
        // connection properties
        Properties jdbcProperties = new Properties();
        try {
            jdbcProperties.load(ServiceLocator.class.getClassLoader().getResourceAsStream("/jdbc.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
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

        SERVICES.put(UserService.class, userService);
        SERVICES.put(ProductService.class, productService);
        SERVICES.put(SecurityService.class, securityService);
    }

    public static <T> T getService(Class<T> clazz) {
        return clazz.cast(SERVICES.get(clazz));
    }

    private static DataSource createDataSource(Properties properties) {
        MysqlConnectionPoolDataSource dataSource = new MysqlConnectionPoolDataSource();
        dataSource.setUrl(properties.getProperty("jdbc.url"));
        dataSource.setUser(properties.getProperty("jdbc.username"));
        dataSource.setPassword(properties.getProperty("jdbc.password"));
        return dataSource;
    }
}
