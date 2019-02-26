package com.anabol.onlineshop.web.controller;

import com.anabol.onlineshop.entity.Product;
import com.anabol.onlineshop.entity.UserRole;
import com.anabol.onlineshop.service.ProductService;
import com.anabol.onlineshop.service.SecurityService;
import com.anabol.onlineshop.web.auth.Session;
import com.anabol.onlineshop.web.templater.PageGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private SecurityService securityService;

    @GetMapping(path = {"/", "/products"})
    public String getProducts(@CookieValue("user-token") String token, ModelMap modelMap) throws IOException {
        modelMap.put("products", productService.findAll());
        modelMap.put("isUserRoleAdmin", isUserRoleAdmin(token));
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

    private boolean isUserRoleAdmin(String token) {
        Session session = securityService.findByToken(token);
        return session != null && UserRole.ADMIN == session.getUserRole();
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }
}
