package com.fundfun.fundfund.controller.user;

import com.fundfun.fundfund.domain.user.*;
import com.fundfun.fundfund.dto.user.UserContext;
import com.fundfun.fundfund.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.security.Principal;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Controller
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

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/curUser")
    @ResponseBody
    public UserContext curUser(@AuthenticationPrincipal UserContext userContext) {
        return userContext;
    }

    /**
     * 로그인 폼 이동
     * @return view
     */
    @GetMapping("/user/login")
    public String login() {
        return "login";
    }

    @GetMapping("/")
    public String index(@AuthenticationPrincipal Users user) {
        System.out.println("user: " + user.toString());
        return "index";
    }

    //test코드
    @PreAuthorize("isAuthenticated()")//로그인 됐을 때 들어갈 수 있는 페이지 //isAnonymous()->비회원도 들어갈 수 있음.
    @GetMapping("/show/user")
    public String showUserInfo(Principal principal, Model model) {

        System.out.println("principal.getName() = "+principal.getName());
        Optional<Users> ou = userService.findByEmail(principal.getName());
        if(ou.isPresent()){
            Users user = ou.get();
            model.addAttribute("user", user);
        }
        return "testwc/test";
    }

}
