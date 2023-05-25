package com.fundfun.fundfund.controller.product;
import com.fundfun.fundfund.domain.product.Product;
import com.fundfun.fundfund.domain.user.UserDTO;
import com.fundfun.fundfund.domain.user.Users;
import com.fundfun.fundfund.dto.product.ProductDto;
import com.fundfun.fundfund.service.order.OrderService;
import com.fundfun.fundfund.service.product.ProductService;
import com.fundfun.fundfund.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.hibernate.id.insert.Binder;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private final ProductService productService;
    private final OrderService orderService;
    private final UserService userService;


    /**
     * 등록 폼 이동
     */
    @GetMapping("/register")
    public String register(ProductDto productDto, MultipartFile thumb) {
        return "product/product_register";
    }


    /**
     * (해당 유저에 해당하는 주문서 ..) 전체검색
     */
//    @GetMapping("/list")
//    public String list(Model model, int modeVal) {
//        if () {
//            List<ProductDto> productList = productService.selectAll();
//            model.addAttribute("list", productList);
//            return "product/product_list";
//        }
//        if (modeVal == 2) {
//            List<Product> productList = productService.selectByStatus("진행중");
//            model.addAttribute("list", productList);
//            return "product/product_list";
//        }
//        return "product/list";
//
//    }

    /**
     * 전체 상품 list 보여주기
     * @param model
     * @return view
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/list")
    public String list(Model model){
        List<ProductDto> productList = productService.selectAll();
        model.addAttribute("list", productList);
        return "product/product_list";
    }


    @GetMapping("/list/progress")
    public String listProgress(Model model){
        List<ProductDto> productList = productService.selectByStatus("진행중");
        System.out.println("product.size() = " + productList.size());
        model.addAttribute("list", productList);
        return "product/product_list";
    }

    /**
     * 상품 등록 처리
     */
    @PostMapping("/write")
    public String write(@Valid ProductDto productDto, BindingResult bindingResult, MultipartFile thumbnailImg, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "product/product_register";
        }
        UserDTO userDTO = userService.findByEmail(principal.getName());
//        if (ou.isPresent()) {
//            Users user = ou.get();
            productService.registerProduct(productDto, thumbnailImg, userDTO);
//        }
//        else{
//            throw new RuntimeException("비회원입니다.");
//        }
        return "redirect:/product/list";
    }

    /**
     * 상품 수정 폼
     */
    @GetMapping("/update/{encId}")
    public String update(@PathVariable String encId, Model model, Principal principal) {
        UserDTO userDTO = userService.findByEmail(principal.getName());
        ProductDto productDto = productService.selectById(orderService.decEncId(encId));
        if (userDTO.getId().equals(productDto.getFundManager().getId())) {
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
        UserDTO userDTO = userService.findByEmail(principal.getName());
        UUID productId = orderService.decEncId(encId);
        productService.update(productId, productDto, thumbnailImg, userDTO);

        return "redirect:/product/list";
    }

    /**
     * 상품 삭제
     */
    @GetMapping("/delete/{encId}")
    public String delete(@PathVariable String encId, Principal principal) {
        UserDTO userDTO = userService.findByEmail(principal.getName());
        ProductDto productDto = productService.selectById(orderService.decEncId(encId));
        if (!userDTO.getId().equals(productDto.getFundManager().getId())) {
            throw new RuntimeException("상품 삭제 권한이 없습니다.");
        }
            UUID productId = orderService.decEncId(encId);
        productService.delete(productId);
        return "redirect:/product/list";

    }

    /**
     * 검색해서 게시글 찾기
     */
    @GetMapping("/search")
    public String search(Model model, String title) {
        List<ProductDto> searchList = productService.searchTitle(title);
        model.addAttribute("searchList", searchList);
        return "product/product_search_list";
    }



}
