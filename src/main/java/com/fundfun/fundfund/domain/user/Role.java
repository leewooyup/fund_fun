package com.fundfun.fundfund.domain.user;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@Getter
public enum Role {
    ADMIN(0),
    COMMON(1),
    FUND_MANAGER(2);

    private int value;

    Role(int i) {
        this.value = value;
    }
}
