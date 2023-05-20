package com.fundfun.fundfund.dto.user;

import com.fundfun.fundfund.domain.user.Users;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

@Getter
public class UserContext extends User {
    public UserContext(Users user, List<GrantedAuthority> authorities) {
        super(user.getEmail(), user.getPassword(), authorities);
    }
}
