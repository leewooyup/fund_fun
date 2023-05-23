package com.fundfun.fundfund.domain.user;

import lombok.Getter;

@Getter
public enum Role {
    COMMON(1),
    FUND_MANAGER(2),
    ADMIN(0);

    private int value;
    Role(int value) {
        this.value = value;
    }
}
