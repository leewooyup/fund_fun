package com.fundfun.fundfund.controller.user;

import com.fundfun.fundfund.domain.user.*;
import com.fundfun.fundfund.dto.user.UserContext;
import com.fundfun.fundfund.service.order.OrderService;
import com.fundfun.fundfund.service.portfolio.PortfolioService;
import com.fundfun.fundfund.service.post.PostService;
import com.fundfun.fundfund.service.product.ProductService;
import com.fundfun.fundfund.service.product.ProductServiceImpl;
import com.fundfun.fundfund.service.user.UserService;
import com.fundfun.fundfund.util.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.security.Principal;
import java.util.Optional;
import java.util.stream.Collectors;

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

        UserDTO user = userService.register(Users.builder()
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
                .build()
        );

        log.info("[UserController] ]User Role {} has been registered.", user.getRole(), user.toString());
        return "redirect:/";
    }

    @GetMapping("/mypage")
    public String myPage(@AuthenticationPrincipal UserAdapter adapter, Model model) {
        UserDTO user = userService.findById(adapter.getUser().getId());

        model.addAttribute("user", user);
        if (user.getRole() == Role.COMMON) {
            model.addAttribute("investment", user.getOrders().stream().map(x -> x.getProduct()).collect(Collectors.toList()));
            model.addAttribute("posts", user.getPosts());

        } else if (user.getRole() == Role.FUND_MANAGER) {
            model.addAttribute("portfolios", user.getOn_vote_portfolio());
            model.addAttribute("products", user.getManaging_product());
        }
        return "index";
    }

    @PostMapping("/mypage")
    public String editMyPage(@AuthenticationPrincipal UserAdapter adapter) {

        return "index";
    }



}
