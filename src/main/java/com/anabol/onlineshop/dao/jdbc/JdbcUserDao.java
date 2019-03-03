package com.anabol.onlineshop.dao.jdbc;

import com.anabol.onlineshop.dao.UserDao;
import com.anabol.onlineshop.entity.User;
import com.anabol.onlineshop.dao.jdbc.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcUserDao implements UserDao {
    private static final UserMapper USER_MAPPER = new UserMapper();
    private static final String FIND_BY_NAME_QUERY = "SELECT name, password, salt, role FROM user WHERE name = ?";
    private static final String INSERT_QUERY = "INSERT INTO user (name, password, salt, role) VALUES (:name, :password, :salt, :role)";

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public JdbcUserDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate, JdbcTemplate jdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User getByName(String name) {
        try {
            return jdbcTemplate.queryForObject(FIND_BY_NAME_QUERY, USER_MAPPER, name);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public void insert(User user) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("name", user.getName());
        parameterSource.addValue("password", user.getPassword());
        parameterSource.addValue("salt", user.getSalt());
        parameterSource.addValue("role", user.getRole());
        namedParameterJdbcTemplate.update(INSERT_QUERY, parameterSource);
    }

}
