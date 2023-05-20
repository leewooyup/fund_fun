package com.fundfun.fundfund.domain.user;

import lombok.Getter;

@Getter
public enum Role {
    COMMON("ROLE_COMMON"),
    FUND_MANAGER("ROLE_MANAGER"),
    ADMIN("ROLE_ADMIN");

    private String value;
    Role(String value) {
        this.value = value;
    }
}
