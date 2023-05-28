package com.fundfun.fundfund.controller;

import com.fundfun.fundfund.domain.post.Post;
import com.fundfun.fundfund.domain.user.Role;
import com.fundfun.fundfund.domain.user.UserAdapter;
import com.fundfun.fundfund.domain.user.UserDTO;
import com.fundfun.fundfund.dto.post.PostDto;
import com.fundfun.fundfund.dto.product.ProductDto;
import com.fundfun.fundfund.service.post.PostService;
import com.fundfun.fundfund.service.product.ProductService;
import com.fundfun.fundfund.util.Util;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final ModelMapper modelMapper;
    private final PostService postService;
    private final ProductService productService;


    @GetMapping("/")
    public String index(@AuthenticationPrincipal UserAdapter adapter, Model model) throws IOException {
        List<ProductDto> productDtoList = productService.selectAll();
        if (productDtoList.size() > 3) {
            List<ProductDto> list = productService.selectByCurrentGoal();
            List<ProductDto> newList = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                newList.add(i, list.get(i));
            }
            model.addAttribute("productList", newList);
        } else {
            model.addAttribute("productList", productDtoList);
        }

        List<PostDto> postDtoList = postService.selectAll();
        if (postDtoList.size() > 5) {
            List<PostDto> newList = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                newList.add(i, postDtoList.get(i));
            }
            model.addAttribute("postList", newList);
        } else {
            model.addAttribute("postList", postDtoList);
        }

        if (adapter == null) {
            return "index";
        } else {
            model.addAttribute("imgDir", Util.findProfileImg(adapter.getUser().getId()));
            if (adapter.getUser().getRole() == Role.COMMON) {
                model.addAttribute("user", adapter.getUser());
                return "index_user";
            } else if (adapter.getUser().getRole() == Role.FUND_MANAGER) {
                model.addAttribute("user", adapter.getUser());
                return "index_fund";
            }
        }

        return "index";
    }

//    @PostMapping("")
}
