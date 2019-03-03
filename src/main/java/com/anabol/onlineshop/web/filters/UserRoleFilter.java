package com.anabol.onlineshop.web.filters;

import com.anabol.onlineshop.entity.UserRole;

public class UserRoleFilter extends RoleFilter {

    @Override
    protected boolean isValidRole(UserRole userRole) {
        return (userRole == UserRole.USER) || (userRole == UserRole.ADMIN);
    }

}