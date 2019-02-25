package com.anabol.onlineshop.web.servlets.product;

import com.anabol.onlineshop.entity.UserRole;
import com.anabol.onlineshop.service.ProductService;
import com.anabol.onlineshop.service.SecurityService;
import com.anabol.onlineshop.web.auth.Session;
import com.anabol.onlineshop.web.templater.PageGenerator;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ShowProductServlet extends HttpServlet {
    private ProductService productService;
    private SecurityService securityService;

    public ShowProductServlet() {
    }

    public ShowProductServlet(ProductService productService, SecurityService securityService) {
        this.productService = productService;
        this.securityService = securityService;
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws IOException {
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("products", productService.findAll());
        pageVariables.put("isUserRoleAdmin", isUserRoleAdmin(request.getCookies()));
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(PageGenerator.instance().getPage("products.html", pageVariables));
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
}
