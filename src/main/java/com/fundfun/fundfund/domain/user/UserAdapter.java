package com.fundfun.fundfund.domain.user;

import lombok.Getter;
import lombok.ToString;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

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

    public static UserDTO toDTO(UserAdapter adapter) {
        return UserDTO.builder()
                .id(adapter.getUser().getId())
                .money(adapter.getUser().getMoney())
                .phone(adapter.getUser().getPhone())
                .count(adapter.getUser().getCount())
                .email(adapter.getUser().getEmail())
                .role(adapter.getUser().getRole())
                .total_investment(adapter.getUser().getTotal_investment())
                .gender(adapter.getUser().getGender())
                .name(adapter.getUser().getName())
                .build();
    }
}
