package com.fundfun.fundfund.domain.alarm;

import com.fundfun.fundfund.domain.user.Users;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AlarmDTO {
    private Long id;
    private String content;
    private boolean is_read;

}
