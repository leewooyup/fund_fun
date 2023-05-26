package com.fundfun.fundfund.controller;

import com.fundfun.fundfund.domain.post.Post;
import com.fundfun.fundfund.domain.user.Role;
import com.fundfun.fundfund.domain.user.UserAdapter;
import com.fundfun.fundfund.domain.user.UserDTO;
import com.fundfun.fundfund.service.post.PostService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final ModelMapper modelMapper;
    private final PostService postService;

    @GetMapping("/")
    public String index(@AuthenticationPrincipal UserAdapter adapter, Model model) {

        if (adapter == null) {
            return "index";
        }else{
            System.out.println("adapter = " + adapter.getUser().getRole());
            if (adapter.getUser().getRole() == Role.COMMON) {
                return "index_user";
            } else if (adapter.getUser().getRole() == Role.FUND_MANAGER) {
                return "index_fund";
            }
        }

        return "index";

    }
}
