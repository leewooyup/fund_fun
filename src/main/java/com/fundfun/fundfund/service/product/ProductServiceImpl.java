package com.fundfun.fundfund.service.product;

import com.fundfun.fundfund.domain.product.Product;
import com.fundfun.fundfund.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{
    private final ProductRepository productRepository;
    @Override
    public int createProduct(){
        Product product = Product.builder()
                .crowdStart("2023-05-15")
                .crowdEnd("2023-07-15")
                .goal(1_000_000)
                .currentGoal(1_500_000)
                .status("진행중")
                .description("펀드진행중")
                .build();

        Product result = productRepository.save(product);
        if(result == null){
            return 0;
        }
        return 1;
    }
}
