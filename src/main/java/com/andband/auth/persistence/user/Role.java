package com.andband.auth.persistence.user;

public enum Role {

    USER("ROLE_USER"),
    INTERNAL_API("ROLE_INTERNAL_API");

    private String name;

    Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
