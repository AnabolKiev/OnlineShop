package com.anabol.onlineshop.web.servlets.product;

import com.anabol.onlineshop.service.ProductService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

public class DeleteProductServlet extends HttpServlet {
    private ProductService productService;

    public DeleteProductServlet() {
    }

    public DeleteProductServlet(ProductService productService) {
        this.productService = productService;
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws IOException {

        int id = Integer.parseInt(request.getPathInfo().replace("/", ""));
        productService.deleteById(id);
        response.sendRedirect("/products");

    }
}
