package com.fundfun.fundfund.domain.alarm;

import com.fundfun.fundfund.domain.user.Users;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlarmDTO {
    private Long id;
    private Users user;
    private String content;
    private boolean is_read;

}
