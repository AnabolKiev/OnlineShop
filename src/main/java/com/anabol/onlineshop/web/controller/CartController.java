package com.anabol.onlineshop.web.controller;

import com.anabol.onlineshop.entity.CartElement;
import com.anabol.onlineshop.entity.Product;
import com.anabol.onlineshop.service.ProductService;
import com.anabol.onlineshop.web.auth.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cart")
public class CartController {
    final ProductService productService;

    @Autowired
    public CartController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/{productId}")
    public String addToCart(@PathVariable int productId, @RequestAttribute Session session) {
        Product product = productService.findById(productId);
        CartElement cartElement = session.getCart().get(productId);
        if (cartElement == null) {
            session.getCart().put(productId, new CartElement(product, 1));
        } else {
            cartElement.incCount();
        }

        return "redirect:/products";
    }

    @GetMapping("")
    public String showCart(@RequestAttribute Session session, ModelMap modelMap) {
        modelMap.put("cart", session.getCart());
        return "cart";
    }

    @GetMapping(path = "/delete/{productId}")
    public String deleteFromCart(@PathVariable int productId, @RequestAttribute Session session) {
        session.getCart().remove(productId);
        return "redirect:/cart";
    }

}
