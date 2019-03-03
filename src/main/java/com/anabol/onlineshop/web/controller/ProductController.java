package com.anabol.onlineshop.web.controller;

import com.anabol.onlineshop.entity.Product;
import com.anabol.onlineshop.entity.UserRole;
import com.anabol.onlineshop.service.ProductService;
import com.anabol.onlineshop.web.auth.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping(path = {"/", "/products"})
    public String getProducts(@RequestAttribute Session session, ModelMap modelMap) throws IOException {
        modelMap.put("products", productService.findAll());
        modelMap.put("isUserRoleAdmin", UserRole.ADMIN == session.getUserRole());
        return "products";
    }

    @GetMapping(path = "/product/add")
    public String addProduct() throws IOException {
        return "add";
    }

    @PostMapping(path = "/product/add")
    public String postProduct(@RequestParam String name, @RequestParam String description,
                              @RequestParam String price) throws IOException {
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(Double.valueOf(price));
        productService.insert(product);
        return "redirect:/products";
    }

    @GetMapping(path = "/product/delete/{productId}")
    public String deleteProduct(@PathVariable int productId) throws IOException {
        productService.deleteById(productId);
        return "redirect:/products";
    }

    @GetMapping(path = "/product/edit/{productId}")
    public String editProduct(@PathVariable int productId, ModelMap modelMap) throws IOException {
        Product product = productService.findById(productId);
        if (product != null) { // specified product was found
            modelMap.put("product", product);
            return "edit";
        } else {
            return "404";
        }
    }

    @PostMapping(path = "/product/edit")
    public String postEditedProduct(@RequestParam int id, @RequestParam String name, @RequestParam String description,
                                    @RequestParam double price) throws IOException {
        Product product = new Product();
        product.setId(id);
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        productService.update(product);
        return "redirect:/products";
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

}
