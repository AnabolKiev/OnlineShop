package com.anabol.onlineshop.dao.jdbc.mapper;

import com.anabol.onlineshop.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper {
    public static User mapRow(ResultSet resultSet) throws SQLException {
            User user = new User();
            user.setName(resultSet.getString("name"));
            user.setPassword(resultSet.getString("password"));
            user.setRole(resultSet.getString("role"));
            return user;
    }
}
