package com.fundfun.fundfund.controller.user;

import com.fundfun.fundfund.config.queryDsl.BaseConfig;
import com.fundfun.fundfund.domain.user.SessionUser;
import com.fundfun.fundfund.domain.user.UserDTO;
import com.fundfun.fundfund.domain.user.Users;
import com.fundfun.fundfund.service.user.UserService;
import com.fundfun.fundfund.util.ApiResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("ff/api/v1/")
@RequiredArgsConstructor
@Slf4j
public class LoginRestController {
    private final UserService userService;
    private final BCryptPasswordEncoder encoder;

    @RequestMapping("login")
    public ApiResponse<SessionUser> login(@RequestBody Map<String, String> map) {
        ModelMapper modelMapper = new ModelMapper();
        String email = map.get("email");
        String pw = map.get("pw");

        Optional<Users> target = userService.findByEmail(email);
        // 2023-05-16_yeoooo : No User Found Exception 추가 필요

        if (encoder.encode(pw) == target.get().getPassword()) {

            return ApiResponse.success(SessionUser.builder()
                    .id(target.get().getId())
                    .count(target.get().getCount())
                    .name(target.get().getName())
                    .phone(target.get().getPhone())
                    .role(target.get().getRole())
                    .money(target.get().getMoney())
                    .gender(target.get().getGender())
                    .benefit(target.get().getBenefit())
                    .email(target.get().getEmail())
                    .total_investment(target.get().getTotal_investment())
                    .build());
        } else {
            return ApiResponse.fail(505, null);

        }
    }
    @RequestMapping("logout")
    public ApiResponse<SessionUser> logout(@RequestBody Map<String, String> map) {
        return ApiResponse.success(new SessionUser());
    }


}
