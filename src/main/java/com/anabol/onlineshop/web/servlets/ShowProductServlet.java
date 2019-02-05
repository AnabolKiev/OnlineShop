package com.anabol.onlineshop.web.servlets;

import com.anabol.onlineshop.service.ProductService;
import com.anabol.onlineshop.web.templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ShowProductServlet extends HttpServlet {
    private ProductService productService;

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("products", productService.findAll());
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(PageGenerator.instance().getPage("products.html", pageVariables));
    }

    public ShowProductServlet(ProductService productService) {
        this.productService = productService;
    }

}
