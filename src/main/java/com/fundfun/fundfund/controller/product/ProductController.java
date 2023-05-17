package com.fundfun.fundfund.controller.product;

import com.fundfun.fundfund.domain.product.Product;
import com.fundfun.fundfund.dto.product.ProductDto;
import com.fundfun.fundfund.service.product.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductServiceImpl productService;

    /**
     * register 폼 이동
     * */
    @GetMapping("/register")
    public String register(){
        return "product/product_register";
    }

    /**
     * (해당 유저에 해당하는 주문서 ..) 전체 검색
     */
    @GetMapping("/list")
    public String list(Model model) {
        List<Product> productList = productService.selectAll();
        model.addAttribute("list", productList);
        return "/product/product_list";
    }

    /**
     * 상품 등록
     */
    @PostMapping("/write")
    public String write(ProductDto productDto) {
        System.out.println(productDto.getDescription());
        productService.registerProduct(productDto);
        return "redirect:/product/list";
    }

    /**
     * 상품 삭제
     * */
    @RequestMapping("/delete")
    public String delete(UUID id, String password){
        productService.delete(id);
        return "redirect:/product/list";
    }

    /**
     * 상세보기 --> order/form으로 이동
     * */
    @GetMapping("/detail/{id}")
    public String detail(){

        return "redirect:/order/form";
    }

    /**
     * 검색해서 게시글 찾기
     * */
    @PostMapping("/search")
    public String search(Model model, String title){
        List<Product> productList = productService.search(title);
        model.addAttribute("list", productList);
        return "redirect:/product/list";
    }


}
