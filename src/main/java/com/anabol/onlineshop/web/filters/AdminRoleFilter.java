package com.anabol.onlineshop.web.filters;

import com.anabol.onlineshop.entity.UserRole;
import com.anabol.onlineshop.service.SecurityService;
import com.anabol.onlineshop.web.auth.Session;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AdminRoleFilter extends GenericFilterBean {
    private SecurityService securityService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (securityService == null) {
            ServletContext servletContext = servletRequest.getServletContext();
            WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
            securityService = webApplicationContext.getBean(SecurityService.class);
        }

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        Cookie[] cookies = httpServletRequest.getCookies();
        boolean isAuth = false;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("user-token".equals(cookie.getName())) {
                    Session session = securityService.findByToken(cookie.getValue());
                    if (session != null) {
                        if (UserRole.ADMIN == session.getUserRole()) {
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

    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }

}