package com.anabol.onlineshop.dao.jdbc.mapper;

import com.anabol.onlineshop.entity.Product;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductMapper implements RowMapper<Product> {

    @Override
    public Product mapRow(ResultSet resultSet, int i) throws SQLException {
        Product product = new Product();
        product.setId(resultSet.getInt("id"));
        product.setName(resultSet.getString("name"));
        product.setDescription(resultSet.getString("description"));
        product.setPrice(resultSet.getDouble("price"));
        return product;
    }
}