package com.fundfun.fundfund.controller.user;

import com.fundfun.fundfund.domain.user.RegisterForm;
import com.fundfun.fundfund.domain.user.SessionUser;
import com.fundfun.fundfund.domain.user.Users;
import com.fundfun.fundfund.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping
@Slf4j
public class LoginController {
    private final UserService userService;


    @GetMapping("/login")
    public String getLoginForm() {
        log.info("[Login Controller] - login page accessed");
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/index";
    }

}
