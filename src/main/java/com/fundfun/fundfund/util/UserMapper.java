package com.fundfun.fundfund.util;

import com.fundfun.fundfund.domain.user.UserDTO;
import com.fundfun.fundfund.domain.user.Users;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;

import javax.transaction.Transactional;


@Transactional
@AllArgsConstructor
public class UserMapper {
    private static ModelMapper modelMapper = new ModelMapper();

    public static UserDTO toDto(Users user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setBenefit(user.getBenefit());
        dto.setCount(user.getCount());
        dto.setMoney(user.getMoney());
        dto.setEmail(user.getEmail());
        dto.setName(user.getName());
        dto.setGender(user.getGender());
        dto.setPhone(user.getPhone());
        dto.setTotal_investment(user.getTotal_investment());
        return dto;
    }
}
