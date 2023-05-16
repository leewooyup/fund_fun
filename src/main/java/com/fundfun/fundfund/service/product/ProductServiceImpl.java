package com.fundfun.fundfund.service.product;

import com.fundfun.fundfund.domain.order.Orders;
import com.fundfun.fundfund.domain.product.Product;
import com.fundfun.fundfund.dto.product.ProductDto;
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

    public int updateProduct(int cost, UUID productId) {
        Product dbProduct = selectById(productId);
        int money = dbProduct.getCurrentGoal() + cost;

        Product product = Product.builder()
                .currentGoal(money)
                .build();
        Product result = productRepository.save(product);

        if (result == null)
            return 0;
        return 1;
    }

    @Override
    public Product registerProduct(ProductDto productDto) {
        Product product = Product.builder()
                .goal(productDto.getGoal())
                .currentGoal(0)
                .crowdStart(productDto.getStartDate())
                .crowdEnd(productDto.getEndDate())
                .description("")
                .status("진행중")
                .build();
        productRepository.save(product);
        return product;
    }

    public int getCurrentCollection(Product product) {
        Product dbProduct = selectById(product.getId());
        return dbProduct.getCurrentGoal();
    }
}

