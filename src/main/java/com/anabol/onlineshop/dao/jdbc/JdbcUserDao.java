package com.anabol.onlineshop.dao.jdbc;

import com.anabol.onlineshop.dao.UserDao;
import com.anabol.onlineshop.entity.User;
import com.anabol.onlineshop.dao.jdbc.mapper.UserMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcUserDao implements UserDao {
    private static final String JDBC_CONNECTION = "jdbc:sqlite:test.db";

    private static final String FIND_ALL_QUERY = "SELECT id, firstName, lastName, salary, dateOfBirth FROM user";
    private static final String FIND_BY_ID_QUERY = "SELECT id, firstName, lastName, salary, dateOfBirth FROM user WHERE id = ?";
    private static final String INSERT_QUERY = "INSERT INTO user (firstName, lastName, salary, dateOfBirth) VALUES (?, ?, ?, ?)";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM user WHERE id = ?";
    private static final String UPDATE_QUERY = "UPDATE user SET firstName = ?, lastName = ?, salary = ?, dateOfBirth = ? WHERE id = ?";

    @Override
    public List<User> findAll() {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(FIND_ALL_QUERY)) {
            List<User> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(UserMapper.mapRow(resultSet));
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("We got SQLException", e);
        }
    }

    @Override
    public User findById(int id) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_QUERY)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return UserMapper.mapRow(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("We got SQLException", e);
        }
    }

    @Override
    public void insert(User user) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY)) {
            setStatementAttributes(preparedStatement, user);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("We got SQLException", e);
        }
    }

    @Override
    public void deleteById(int id) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_ID_QUERY)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("We got SQLException", e);
        }
    }

    @Override
    public void update(User user) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUERY)) {
            setStatementAttributes(preparedStatement, user);
            preparedStatement.setInt(5, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("We got SQLException", e);
        }
    }

    private void setStatementAttributes(PreparedStatement preparedStatement, User user) throws SQLException {
        preparedStatement.setString(1, user.getFirstName());
        preparedStatement.setString(2, user.getLastName());
        preparedStatement.setDouble(3, user.getSalary());
        preparedStatement.setString(4, user.getDateOfBirth().toString());
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_CONNECTION);
    }
}
