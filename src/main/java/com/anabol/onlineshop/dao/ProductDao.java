package com.anabol.onlineshop.dao;

import com.anabol.onlineshop.entity.Product;

import java.util.List;

public interface ProductDao {
    List<Product> findAll();

    Product findById(int id);

    void insert(Product user);

    void deleteById(int id);

    void update(Product user);
}
