package com.fundfun.fundfund.domain.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class RegisterForm {
    public String password;
    public String phone;
    public String name;
    public String email;
    public String gender;

}
