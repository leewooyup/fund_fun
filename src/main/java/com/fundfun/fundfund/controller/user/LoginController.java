package com.fundfun.fundfund.controller.user;

import com.fundfun.fundfund.domain.user.RegisterForm;
import com.fundfun.fundfund.domain.user.SessionUser;
import com.fundfun.fundfund.domain.user.Users;
import com.fundfun.fundfund.service.user.UserService;
import lombok.RequiredArgsConstructor;
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
public class LoginController {
    private final UserService userService;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public String login(String email, String password, HttpSession session) {
        Optional<Users> target = userService.findByEmail(email);
        if (encoder.encode(password) == target.get().getPassword()) {
            session.setAttribute("authenticated", SessionUser.builder()
                                                                    .gender(target.get().getGender())
                                                                    .count(target.get().getCount())
                                                                    .money(target.get().getMoney())
                                                                    .phone(target.get().getPhone())
                                                                    .email(target.get().getEmail())
                                                                    .role(target.get().getRole())
                                                                    .name(target.get().getName())
                                                                    .build());
        }

        return "redirect:/";
    }

    @GetMapping("/login")
    public String getLoginForm() {
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/index";
    }

}
