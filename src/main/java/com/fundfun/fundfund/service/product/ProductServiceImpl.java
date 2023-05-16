package com.fundfun.fundfund.service.product;

import com.fundfun.fundfund.domain.order.Orders;
import com.fundfun.fundfund.domain.product.Product;
import com.fundfun.fundfund.repository.product.ProductRepository;
import com.fundfun.fundfund.service.order.OrderServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    public void createProduct2() {
        Product product = Product.builder()
                .crowdStart("2023-08-15")
                .crowdEnd("2023-12-15")
                .goal(24)
                .currentGoal(66)
                .status("진행마감")
                .description("펀드진행중")
                .build();

        productRepository.save(product);
    }


    public List<Product> selectAll() {
        return productRepository.findAll();
    }

    public void insert(Product product) {
        productRepository.save(product);
    }

    public Product update(Product product) {
        return productRepository.save(product);
    }

    public void delete(UUID id) {
        Product dbProduct = productRepository.findById(id).orElse(null);
        productRepository.delete(dbProduct);
    }

    public Product selectById(UUID id) {
        return productRepository.findById(id).orElse(null);
    }

    public int updateProduct(int cost) {
        Product product = new Product();
        int money = product.getCurrentGoal() + cost;
        Product result = productRepository.save(product);

        Product product1 = Product.builder()
                .currentGoal(money)
                .build();

        if (result == null)
            return 0;
        return 1;
    }

//    public int getCurrentCollection(UUID id) {
//        Product product = selectById(id);
//
//        return product.getCurrentGoal();
//    }
}

