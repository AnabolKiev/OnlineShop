package com.anabol.onlineshop.web.controller;

import com.anabol.onlineshop.entity.Product;
import com.anabol.onlineshop.entity.UserRole;
import com.anabol.onlineshop.service.ProductService;
import com.anabol.onlineshop.service.SecurityService;
import com.anabol.onlineshop.web.auth.Session;
import com.anabol.onlineshop.web.templater.PageGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
    public void getProducts(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("products", productService.findAll());
        pageVariables.put("isUserRoleAdmin", isUserRoleAdmin(request.getCookies()));
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(PageGenerator.instance().getPage("products.html", pageVariables));
    }

    @GetMapping(path = "/product/add")
    public void addProduct(HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(PageGenerator.instance().getPage("add.html"));
    }

    @PostMapping(path = "/product/add")
    public void postProduct(HttpServletRequest request,
                       HttpServletResponse response) throws IOException {
        Product product = new Product();
        product.setName(request.getParameter("name"));
        product.setDescription(request.getParameter("description"));
        product.setPrice(Double.valueOf(request.getParameter("price")));
        productService.insert(product);

        response.sendRedirect(request.getContextPath() + "/products");
    }

    @GetMapping(path = "/product/delete/{productId}")
    public void deleteProduct(@PathVariable int productId, HttpServletResponse response) throws IOException {
        productService.deleteById(productId);
        response.sendRedirect("/products");
    }

    @GetMapping(path = "/product/edit/{productId}")
    public void editProduct(@PathVariable int productId, HttpServletResponse response) throws IOException {
        Product product = productService.findById(productId);
        if (product != null) { // specified product was found
            Map<String, Object> pageVariables = new HashMap<>();
            pageVariables.put("product", product);

            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().println(PageGenerator.instance().getPage("edit.html", pageVariables));
        } else {
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
//                response.getWriter().println(PageGenerator.instance().getPage("edit.html", pageVariables));
        }
    }

    @PostMapping(path = "/product/edit")
    public void postEditedProduct(HttpServletRequest request,
                       HttpServletResponse response) throws IOException {
        Product product = new Product();
        product.setId(Integer.parseInt(request.getParameter("id")));
        product.setName(request.getParameter("name"));
        product.setDescription(request.getParameter("description"));
        product.setPrice(Double.valueOf(request.getParameter("price")));
        productService.update(product);

        response.sendRedirect("/products");
    }

    private boolean isUserRoleAdmin(Cookie[] cookies) {
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("user-token".equals(cookie.getName())) {
                    Session session = securityService.findByToken(cookie.getValue());
                    if (session != null && UserRole.ADMIN == session.getUserRole()) {
                        return true;
                    }
                    break;
                }
            }
        }
        return false;
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }
}
