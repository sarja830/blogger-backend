package com.blog.demo.model.user;

// Define enum for role types with integer values
public enum RoleType {
    ROLE_ADMIN(2), ROLE_USER(0), ROLE_AUTHOR(2);

    private final int value;

    RoleType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}