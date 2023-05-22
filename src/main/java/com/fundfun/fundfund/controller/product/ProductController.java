package com.fundfun.fundfund.controller.product;

import com.fundfun.fundfund.domain.product.Product;
import com.fundfun.fundfund.domain.user.UserDTO;
import com.fundfun.fundfund.domain.user.Users;
import com.fundfun.fundfund.dto.product.ProductDto;
import com.fundfun.fundfund.service.order.OrderServiceImpl;
import com.fundfun.fundfund.service.product.ProductServiceImpl;
import com.fundfun.fundfund.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductServiceImpl productService;
    private final OrderServiceImpl orderService;
    private final UserService userService;

    /**
     * register 폼 이동
     */
    @GetMapping("/register")
    public String register(ProductDto productDto, MultipartFile thumb) {
        return "product/product_register";
    }


    /**
     * (해당 유저에 해당하는 주문서 ..) 전체 검색
     */
    @GetMapping("/list")
    public String list(Model model, @RequestParam(defaultValue = "1") Integer mode) {
        if (mode == 1) {
            List<Product> productList = productService.selectAll();
            model.addAttribute("list", productList);
            return "product/product_list";
        }
        if (mode == 2) {
            List<Product> productList = productService.selectByStatus("진행중");
            model.addAttribute("list", productList);
            return "product/product_list";
        }
        return "product/list?mode=" + mode;

    }


    /**
     * 상품 등록
     */
    @PostMapping("/write")
    public String write(@Valid ProductDto productDto, BindingResult bindingResult, MultipartFile thumbnailImg, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "product/product_register";
        }
        Optional<Users> ou = userService.findByEmail(principal.getName());
        if (ou.isPresent()) {
            Users user = ou.get();
            productService.registerProduct(productDto, thumbnailImg, user);
        }
        return "redirect:/product/list?mode=" + 1;
    }

    /**
     * 상품 수정 폼
     */
    @GetMapping("/update/{encId}")
    public String update(@PathVariable String encId, Model model, Principal principal) {
        Users user = userService.findByEmail(principal.getName()).orElse(null);
        ProductDto productDto = productService.selectById(orderService.decEncId(encId));

        if (user == productDto.getFundManager()) {
            model.addAttribute("product", productDto);
            model.addAttribute("encId", encId);

            return "product/product_update";
        } else {
            throw new RuntimeException("상품 수정 권한이 없습니다.");
        }
    }


    /**
     * 상품 수정 처리
     */
    @PostMapping("/update/{encId}")
    public String updateProduct(@PathVariable String encId, ProductDto productDto, MultipartFile thumbnailImg, Principal principal) {
        Users user = userService.findByEmail(principal.getName()).orElse(null);
        UUID productId = orderService.decEncId(encId);
        productService.update(productId, productDto, thumbnailImg, user);

        return "redirect:/product/list?mode=" + 1;
    }

    /**
     * 상품 삭제
     */
    @GetMapping("/delete/{encId}")
    public String delete(@PathVariable String encId, Principal principal) {
        Users user = userService.findByEmail(principal.getName()).orElse(null);
        UUID productId = orderService.decEncId(encId);
        productService.delete(productId, user);
        return "redirect:/product/list?mode=" + 1;

    }

    /**
     * 검색해서 게시글 찾기
     */
    @GetMapping("/search")
    public String search(Model model, String title) {
        List<Product> searchList = productService.searchTitle(title);
        model.addAttribute("searchList", searchList);
        return "product/product_search_list";
    }


}
