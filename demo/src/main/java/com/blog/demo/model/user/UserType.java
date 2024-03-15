package com.blog.demo.model.user;

// Define enum for role types with integer values
public enum UserType {
    ROLE_ADMIN(1), ROLE_USER(2);

    private final int value;

    UserType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}