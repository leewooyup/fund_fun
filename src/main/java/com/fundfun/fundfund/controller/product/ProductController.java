package com.fundfun.fundfund.controller.product;

import com.fundfun.fundfund.domain.product.Product;
import com.fundfun.fundfund.dto.product.ProductDto;
import com.fundfun.fundfund.service.product.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductServiceImpl productService;
    private String productTitle;
    /**
     * register 폼 이동
     * */
    @GetMapping("/register")
    public String register(){
        return "product/product_register";
    }

    @PostMapping("/regist")
    public String regist(ProductDto productDto){
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

    @GetMapping("/searchList")
    public String listSearch(Model model){
        List<Product> searchList = productService.search(productTitle);
        model.addAttribute("searchList", searchList);
        return "product/product_search_list";
    }

    /**
     * 상품 등록
     */
    @PostMapping("/write")
    public String write(@Valid ProductDto productDto, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "product/product_register";
        }
        System.out.println(productDto.getDescription());
        productService.registerProduct(productDto);
        return "redirect:/product/list";
    }

    /**
     * 주문서 상세보기
     */
    @GetMapping("/read/{id}")
    public String read(UUID id) {
        Product product = productService.selectById(id);
        return "";
    }


    /**
     * 상품 삭제
     * */
//    @PostMapping("/delete")
//    public String delete(UUID id, String password){
//        productService.delete(id);
//        return "redirect:/product/list";
//    }

    /**
     * 상세보기 --> order/form으로 이동
     * */
//    @GetMapping("/detail/{id}")
//    public String detail(){
//
//        return "/order/form";
//    }

    /**
     * 검색해서 게시글 찾기
     * */
    @PostMapping ("/search")
    public String search(Model model, String title){
        productTitle = title;
        return "redirect:/product/searchList";
    }


}
