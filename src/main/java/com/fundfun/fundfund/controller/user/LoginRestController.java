package com.fundfun.fundfund.controller.user;

import com.fundfun.fundfund.config.auth.JwtTokenProvider;
import com.fundfun.fundfund.domain.user.Users;
import com.fundfun.fundfund.service.user.UserService;
import com.fundfun.fundfund.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/ff/api/v1/")
@RequiredArgsConstructor
@Slf4j
public class LoginRestController {
    private final UserService userService;
    private final BCryptPasswordEncoder encoder;
    private final JwtTokenProvider jwtTokenProvider;

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public ApiResponse<String> login(@RequestBody Map<String, Object> map) {
        String email = map.get("email").toString();
        String pw = encoder.encode(map.get("pw").toString());

        log.info("login attempted : id -> {}, pw -> {}", email, pw);
        Users target = Optional.ofNullable(userService.findByEmail(email).orElseThrow(RuntimeException::new)).get();
        // 2023-05-16_yeoooo : No User Found Exception 추가 필요
        return ApiResponse.success(jwtTokenProvider.createToken(target.getEmail(), target.getRole()));

    }


    @RequestMapping("logout")
    public ApiResponse<String> logout(@RequestBody Map<String, String> map) {
        return ApiResponse.success("success");
    }


}
