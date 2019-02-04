package com.anabol.onlineshop.service.impl;

import com.anabol.onlineshop.dao.ProductDao;
import com.anabol.onlineshop.entity.Product;
import com.anabol.onlineshop.service.ProductService;

import java.util.List;

public class DefaultProductService implements ProductService{

    private ProductDao productDao;

    @Override
    public List<Product> findAll() {
        return productDao.findAll();
    }

    @Override
    public Product findById(int id) {
        return productDao.findById(id);
    }

    @Override
    public void insert(Product product) {
        productDao.insert(product);
    }

    @Override
    public void deleteById(int id) {
        productDao.deleteById(id);
    }

    @Override
    public void update(Product product) {
        productDao.update(product);
    }

    public void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
    }
}
