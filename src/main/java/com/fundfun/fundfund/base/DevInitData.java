package com.fundfun.fundfund.base;

import com.fundfun.fundfund.domain.product.Product;
import com.fundfun.fundfund.service.order.OrderServiceImpl;
import com.fundfun.fundfund.service.product.ProductServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.UUID;

@Configuration
@Profile("dev") // 이 클래스에 정의된 Bean 들은 dev 모드에서만 활성화된다.
public class DevInitData {
    // CommandLineRunner: 앱 실행 직후 초기데이터 세팅 및 초기화에 사용된다.
    @Bean
    CommandLineRunner init(ProductServiceImpl productService, OrderServiceImpl orderService) {
        return args -> {
            Product product = productService.createProduct();
            orderService.createOrder(3000000, product);
        };
    }
}