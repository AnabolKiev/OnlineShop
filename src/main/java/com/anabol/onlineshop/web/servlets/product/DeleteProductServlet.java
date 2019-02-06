package com.anabol.onlineshop.web.servlets.product;

import com.anabol.onlineshop.service.ProductService;
import com.anabol.onlineshop.web.auth.TokenUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class DeleteProductServlet extends HttpServlet {
    private ProductService productService;
    private List<String> tokens;

    public void doGet(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        if (!TokenUtils.isTokenValid(request, tokens)) {
            response.sendRedirect("/login");
        } else {
            int id = Integer.parseInt(request.getPathInfo().replace("/", ""));
            productService.deleteById(id);
            response.sendRedirect("/products");
        }
    }

    public DeleteProductServlet(ProductService productService, List<String> tokens) {
        this.productService = productService;
        this.tokens = tokens;
    }
}
