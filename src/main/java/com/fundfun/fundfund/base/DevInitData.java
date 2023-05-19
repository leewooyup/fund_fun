package com.fundfun.fundfund.base;

import com.fundfun.fundfund.domain.order.Orders;
import com.fundfun.fundfund.domain.product.Product;
import com.fundfun.fundfund.domain.user.Users;
import com.fundfun.fundfund.service.order.OrderServiceImpl;
import com.fundfun.fundfund.service.product.ProductServiceImpl;
import com.fundfun.fundfund.service.user.UserServiceImpl;
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
    CommandLineRunner init(ProductServiceImpl productService, OrderServiceImpl orderService, UserServiceImpl userService) {
        return args -> {
            Users users = userService.createUser();
            Product product = productService.createProduct();
            Orders order = orderService.createOrder(300L, product, users);

            System.out.print(productService.crowdDeadline(product));

//
//            Product product2 = productService.createProduct2();
//            orderService.createOrder(20L, product2, users);

        };
    }
}