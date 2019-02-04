package com.anabol.onlineshop.service;

import com.anabol.onlineshop.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> findAll();

    Product findById(int id);

    void insert(Product product);

    void deleteById(int id);

    void update(Product product);
}
