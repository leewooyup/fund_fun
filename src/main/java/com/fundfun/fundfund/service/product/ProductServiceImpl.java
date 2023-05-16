package com.fundfun.fundfund.service.product;

import com.fundfun.fundfund.domain.order.Orders;
import com.fundfun.fundfund.domain.product.Product;
import com.fundfun.fundfund.repository.product.ProductRepository;
import com.fundfun.fundfund.service.order.OrderServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public Product createProduct() {
        Product product = Product.builder()
                .crowdStart("2023-05-15")
                .crowdEnd("2023-07-15")
                .goal(1000000)
                .currentGoal(1500000)
                .status("진행중")
                .description("펀드진행중")
                .build();

        productRepository.save(product);
        return product;
    }
}
