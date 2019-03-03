package com.anabol.onlineshop.entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CartElement
{
    private final Product product;
    private int count;

    public CartElement(Product product, int count) {
        this.product = product;
        this.count = count;
    }

    public void incCount() {
        count++;
    }

    public void decCount() {
        count--;
    }
}
