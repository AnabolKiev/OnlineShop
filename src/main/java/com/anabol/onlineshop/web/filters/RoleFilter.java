package com.anabol.onlineshop.web.filters;

import com.anabol.onlineshop.entity.UserRole;
import com.anabol.onlineshop.service.SecurityService;
import com.anabol.onlineshop.web.auth.Session;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

abstract class RoleFilter implements Filter {
    private SecurityService securityService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        ServletContext servletContext = filterConfig.getServletContext();
        WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
        securityService = webApplicationContext.getBean(SecurityService.class);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        Cookie[] cookies = httpServletRequest.getCookies();
        boolean isAuth = false;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("user-token".equals(cookie.getName())) {
                    Session session = securityService.findByToken(cookie.getValue());
                    if (session != null) {
                        if (isValidRole(session.getUserRole())) {
                            isAuth = true;
                        }
                    }
                    break;
                }
            }
        }

        if (isAuth) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } else {
            httpServletResponse.sendRedirect("/login");
        }
    }

    @Override
    public void destroy() {

    }

    protected abstract boolean isValidRole(UserRole userRole);
}
