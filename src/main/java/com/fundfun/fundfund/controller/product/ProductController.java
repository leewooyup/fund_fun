package com.fundfun.fundfund.controller.product;

import com.fundfun.fundfund.domain.product.Product;
import com.fundfun.fundfund.dto.product.ProductDto;
import com.fundfun.fundfund.service.order.OrderServiceImpl;
import com.fundfun.fundfund.service.product.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.hibernate.id.insert.Binder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductServiceImpl productService;
    private final OrderServiceImpl orderService;

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
    public String list(Model model) {
        List<Product> productList = productService.selectAll();
        model.addAttribute("list", productList);
        return "product/product_list";
    }

    /**
     * 상품 등록
     */
    @PostMapping("/write")
    public String write(@Valid ProductDto productDto, BindingResult bindingResult, MultipartFile thumbnailImg) {
        if (bindingResult.hasErrors()) {
            return "product/product_register";
        }
        System.out.println("thumbnailImg: " + thumbnailImg);
        productService.registerProduct(productDto, thumbnailImg);
        return "redirect:/product/list";
    }

    /**
     * 상품 수정 폼
     */
    @GetMapping("/update/{encId}")
    public String update(@PathVariable String encId, Model model) {
        if (encId != null) {
            Product product = productService.selectById(orderService.decEncId(encId));
            model.addAttribute("product", product);
            System.out.println("product.title: " + product.getTitle());
            model.addAttribute("encId",encId);
        }
        System.out.println("update encId = " + encId);
        return "product/product_update";
    }

    /**
     * 상품 수정 처리
     */
    @PostMapping("/update/{encId}")
    public String updateProduct(@PathVariable String encId, Model model) {

        return "redirect:/product/list";
    }




        /**
         * 상품 삭제
         */
    @PostMapping("/delete")
    public String delete(UUID id) {
        productService.delete(id);
        return "redirect:/product/list";
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
