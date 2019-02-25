package com.anabol.onlineshop.web.servlets.product;

import com.anabol.onlineshop.entity.Product;
import com.anabol.onlineshop.service.ProductService;
import com.anabol.onlineshop.web.templater.PageGenerator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddProductServlet extends HttpServlet {
    private ProductService productService;

    public AddProductServlet() {
    }

    public AddProductServlet(ProductService productService) {
        this.productService = productService;
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(PageGenerator.instance().getPage("add.html"));
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws IOException {
        Product product = new Product();
        product.setName(request.getParameter("name"));
        product.setDescription(request.getParameter("description"));
        product.setPrice(Double.valueOf(request.getParameter("price")));
        productService.insert(product);

        response.sendRedirect(request.getContextPath() + "/products");
    }
}
