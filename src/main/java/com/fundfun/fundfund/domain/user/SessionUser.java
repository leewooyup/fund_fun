package com.fundfun.fundfund.domain.user;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SessionUser {
    private UUID id;
    private String name;
    private String email;
    private Role role;
    private String phone;
    private Gender gender;
    private LocalDateTime reg_date;
    private Long money;
    private Long count;
    private Long total_investment;
    private Long benefit;
}
