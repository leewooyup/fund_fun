package com.fundfun.fundfund.controller.user;

import com.fundfun.fundfund.domain.user.*;
import com.fundfun.fundfund.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping("/register")
    public String getRegisterForm() {
        return "register";
    }

    @PostMapping("register")
    public String register(RegisterForm form, @RequestParam("role") String role) {

        Users user = userService.register(
            userService.register(Users.builder()
                    .count(0L)
                    .email(form.getEmail())
                    .gender(Gender.valueOf(form.getGender()))
                    .money(0L)
                    .benefit(0L)
                    .name(form.getName())
                    .phone(form.getPhone())
                    .password(form.getPassword())
                    .role(Role.valueOf(role))
                    .total_investment(0L)
                    .build())
        );
        log.info("[UserController] ]User Role {} has been registered.", user.getRole(), user.toString());
        return "redirect:/";
    }

}
