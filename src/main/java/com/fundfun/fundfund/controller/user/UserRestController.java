package com.fundfun.fundfund.controller.user;

import com.fundfun.fundfund.domain.user.Gender;
import com.fundfun.fundfund.domain.user.RegisterForm;
import com.fundfun.fundfund.domain.user.Role;
import com.fundfun.fundfund.domain.user.Users;
import com.fundfun.fundfund.service.user.UserService;
import com.fundfun.fundfund.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("ff/api/v1/")
@RequiredArgsConstructor
@Slf4j
public class UserRestController {
    private final UserService userService;

    @PostMapping("register")
    public ApiResponse<String> register(@RequestBody Map<String, Object> resp, @RequestParam("role") String role) {

        try {
            Users user = userService.register(
                    userService.register(Users.builder()
                            .count(0L)
                            .email((String) resp.get("email"))
                            .gender(Gender.valueOf((String) resp.get("gender")))
                            .money(0L)
                            .benefit(0L)
                            .name((String) resp.get("name"))
                            .phone((String) resp.get("phone"))
                            .password((String) resp.get("pw"))
                            .role(Role.valueOf(role))
                            .total_investment(0L)
                            .build())
            );
            log.info("[UserController] ]User Role {} has been registered.", user.getRole(), user.toString());
            return ApiResponse.success("success");

        } catch (RuntimeException e) {
            // 2023-05-17_yeoooo : RuntimeException은 적절한 예외로 대체되어야 함.
            e.printStackTrace();
            return ApiResponse.fail(500, e.getMessage());
        }
    }


}
