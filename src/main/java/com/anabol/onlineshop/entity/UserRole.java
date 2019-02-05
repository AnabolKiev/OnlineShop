package com.anabol.onlineshop.entity;

public enum UserRole {
    ADMIN("ADMIN");

    private final String name;

    public static UserRole getByName(String name) {
        UserRole[] userRoles = UserRole.values();
        for (UserRole userRole : userRoles) {
            if (userRole.getName().equalsIgnoreCase(name)) {
                return userRole;
            }
        }
        throw new IllegalArgumentException("No role for name " + name);
    }

    UserRole(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
