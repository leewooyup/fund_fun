package com.fundfun.fundfund.controller.product;

import com.fundfun.fundfund.domain.product.Product;
import com.fundfun.fundfund.service.product.ProductService;
import com.fundfun.fundfund.service.product.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
     * (해당 유저에 해당하는 주문서 ..) 전체 검색
     */
    @GetMapping("/list")
    public String list(Model model) {
        List<Product> productList = productService.selectAll();
        model.addAttribute("list", productList);
        return "index";
    }

    /**
     * 상품 등록
     */
    @PostMapping("/write")
    public String write(Product product) {
        productService.insert(product);
        return "product/product_register";
    }

    /**
     * 주문서 상세보기
     */
    @GetMapping("/read/{id}")
    public String read(UUID id) {
        Product product = productService.selectById(id);
        return "";
    }
}
