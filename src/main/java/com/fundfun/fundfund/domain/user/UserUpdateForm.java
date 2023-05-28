package com.fundfun.fundfund.domain.user;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserUpdateForm {

    public String email;
    public String phone;
    public String image;

}
