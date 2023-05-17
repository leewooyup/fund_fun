package com.fundfun.fundfund.controller.user;

import com.fundfun.fundfund.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("ff/api/v1/")
@RequiredArgsConstructor
public class UserRestController {
    private final UserService userService;

}
