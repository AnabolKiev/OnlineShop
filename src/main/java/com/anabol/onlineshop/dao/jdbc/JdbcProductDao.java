package com.anabol.onlineshop.dao.jdbc;

import com.anabol.onlineshop.dao.ProductDao;
import com.anabol.onlineshop.dao.jdbc.connection.DBConnection;
import com.anabol.onlineshop.dao.jdbc.mapper.ProductMapper;
import com.anabol.onlineshop.entity.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcProductDao implements ProductDao {
    private DBConnection dbConnection;

    private static final String FIND_ALL_QUERY = "SELECT id, name, description, price FROM product";

    private static final String FIND_BY_ID_QUERY = "SELECT id, name, description, price FROM product WHERE id = ?";
    private static final String INSERT_QUERY = "INSERT INTO product (name, description, price) VALUES (?, ?, ?)";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM product WHERE id = ?";
    private static final String UPDATE_QUERY = "UPDATE product SET name = ?, description = ?, price = ? WHERE id = ?";

    @Override
    public List<Product> findAll() {
        try (Connection connection = dbConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(FIND_ALL_QUERY)) {
            List<Product> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(ProductMapper.mapRow(resultSet));
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("We got SQLException", e);
        }
    }

    @Override
    public Product findById(int id) {
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_QUERY)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                Product product = null;
                if (resultSet.next()) {
                    product = ProductMapper.mapRow(resultSet);
                }
                return product;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("We got SQLException", e);
        }
    }

    @Override
    public void insert(Product product) {
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY)) {
            setStatementAttributes(preparedStatement, product);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("We got SQLException", e);
        }
    }

    @Override
    public void deleteById(int id) {
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_ID_QUERY)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("We got SQLException", e);
        }
    }

    @Override
    public void update(Product product) {
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUERY)) {
            setStatementAttributes(preparedStatement, product);
            preparedStatement.setInt(4, product.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("We got SQLException", e);
        }
    }

    private void setStatementAttributes(PreparedStatement preparedStatement, Product product) throws SQLException {
        preparedStatement.setString(1, product.getName());
        preparedStatement.setString(2, product.getDescription());
        preparedStatement.setDouble(3, product.getPrice());
    }

    public JdbcProductDao(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }
}
