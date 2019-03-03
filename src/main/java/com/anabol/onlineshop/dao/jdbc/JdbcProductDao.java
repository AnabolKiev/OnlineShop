package com.anabol.onlineshop.dao.jdbc;

import com.anabol.onlineshop.dao.ProductDao;
import com.anabol.onlineshop.dao.jdbc.mapper.ProductMapper;
import com.anabol.onlineshop.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class JdbcProductDao implements ProductDao {
    private static final ProductMapper PRODUCT_MAPPER = new ProductMapper();

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final String FIND_ALL_QUERY = "SELECT id, name, description, price FROM product";
    private static final String FIND_BY_ID_QUERY = "SELECT id, name, description, price FROM product WHERE id = ?";
    private static final String INSERT_QUERY = "INSERT INTO product (name, description, price) VALUES (:name, :description, :price)";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM product WHERE id = ?";
    private static final String UPDATE_QUERY = "UPDATE product SET name = :name, description = :description, price = :price WHERE id = :id";

    @Autowired
    public JdbcProductDao(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<Product> findAll() {
        return jdbcTemplate.query(FIND_ALL_QUERY, PRODUCT_MAPPER);
    }

    @Override
    public Product findById(int id) {
        return jdbcTemplate.queryForObject(FIND_BY_ID_QUERY, PRODUCT_MAPPER, id);
    }

    @Override
    public void insert(Product product) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("name", product.getName());
        parameterSource.addValue("description", product.getDescription());
        parameterSource.addValue("price", product.getPrice());
        namedParameterJdbcTemplate.update(INSERT_QUERY, parameterSource);
    }

    @Override
    public void deleteById(int id) {
        jdbcTemplate.update(DELETE_BY_ID_QUERY, id);
    }

    @Override
    public void update(Product product) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("name", product.getName());
        parameterSource.addValue("description", product.getDescription());
        parameterSource.addValue("price", product.getPrice());
        parameterSource.addValue("id", product.getId());
        namedParameterJdbcTemplate.update(UPDATE_QUERY, parameterSource);
    }

}
