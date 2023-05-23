package com.fundfun.fundfund.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fundfun.fundfund.domain.user.Role;
import com.fundfun.fundfund.domain.user.Users;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class UserContext extends User implements OAuth2User {
    private Map<String, Object> attributes;
    private String userNameAttributeName;

    private final String nickname;
    private final Long money;

    public UserContext(Users user, List<GrantedAuthority> authorities) {
        super(user.getEmail(), user.getPassword(), authorities);
        this.nickname = user.getUsername();
        this.money = user.getMoney();
    }

    public UserContext(Users user, List<GrantedAuthority> authorities, Map<String, Object> attributes, String userNameAttributeName) {
        this(user, authorities);
        this.attributes = attributes;
        this.userNameAttributeName = userNameAttributeName;
    }

    @Override
    public Set<GrantedAuthority> getAuthorities() {
        return super.getAuthorities().stream().collect(Collectors.toSet());
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

    @Override
    @JsonIgnore
    public String getName() {
        return this.getAttribute(this.userNameAttributeName).toString();
    }
}
