package com.fundfun.fundfund.controller.product;

import com.fundfun.fundfund.domain.product.Product;
import com.fundfun.fundfund.dto.product.ProductDto;
import com.fundfun.fundfund.dto.product.ProductShowDTO;
import com.fundfun.fundfund.modelmapper.ModelMapperConfig;
import com.fundfun.fundfund.service.product.ProductService;
import com.fundfun.fundfund.util.ApiResponse;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/ff/api/v5")
@RequiredArgsConstructor
@Slf4j
public class ProductRestController {
    private final ProductService productService;
    private final ModelMapper modelMapper;
    @GetMapping("/show/all")
    public List<ProductShowDTO> showAll(HttpServletResponse response) {
        List<ProductDto> productDtos = productService.selectAll();
        List<ProductShowDTO> products = productDtos
                .stream()
                .map((x) ->
                        modelMapper.map(x, ProductShowDTO.class)
                )
                .collect(Collectors.toList());
        return products;
    }
}
