package com.anabol.onlineshop.dao.jdbc.mapper;

import com.anabol.onlineshop.entity.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        User user = new User();
        user.setName(resultSet.getString("name"));
        user.setPassword(resultSet.getString("password"));
        user.setSalt(resultSet.getString("salt"));
        user.setRole(resultSet.getString("role"));
        return user;
    }
}
