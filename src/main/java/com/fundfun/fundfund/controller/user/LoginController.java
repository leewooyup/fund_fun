package com.fundfun.fundfund.controller.user;

import com.fundfun.fundfund.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping
@Slf4j
public class LoginController {

    @GetMapping("/login")
    public String getLoginForm() {
        log.info("[Login Controller] - login page accessed");
        return "login";
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/";
    }
}
