package com.fundfun.fundfund.domain.user;

import lombok.Getter;
import lombok.ToString;
import java.util.Map;

@Getter
@ToString
public class UserAdapter extends CustomUserDetails {
    private Users user;
    private Map<String, Object> attributes;

    public UserAdapter(Users user){
        super(user);
        this.user = user;
    }

    public UserAdapter(Users user, Map<String, Object> attributes){
        super(user, attributes);
        this.user = user;
        this.attributes = attributes;
    }
}
