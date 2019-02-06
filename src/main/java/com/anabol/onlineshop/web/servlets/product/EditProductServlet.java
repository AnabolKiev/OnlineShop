package com.anabol.onlineshop.web.servlets.product;

import com.anabol.onlineshop.entity.Product;
import com.anabol.onlineshop.service.ProductService;
import com.anabol.onlineshop.web.auth.TokenUtils;
import com.anabol.onlineshop.web.templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditProductServlet extends HttpServlet {
    private ProductService productService;
    private List<String> tokens;

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        if (!TokenUtils.isTokenValid(request, tokens)) {
            response.sendRedirect("/login");
        } else {
            int id = Integer.parseInt(request.getPathInfo().replace("/", ""));
            Product product = productService.findById(id);
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
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        Product product = new Product();
        product.setId(Integer.parseInt(request.getParameter("id")));
        product.setName(request.getParameter("name"));
        product.setDescription(request.getParameter("description"));
        product.setPrice(Double.valueOf(request.getParameter("price")));
        productService.update(product);

        response.sendRedirect("/products");
    }

    public EditProductServlet(ProductService productService, List<String> tokens) {
        this.productService = productService;
        this.tokens = tokens;
    }

}
