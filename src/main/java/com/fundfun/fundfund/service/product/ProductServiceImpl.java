package com.fundfun.fundfund.service.product;

import com.fundfun.fundfund.domain.product.Product;
import com.fundfun.fundfund.dto.product.ProductDto;
import com.fundfun.fundfund.repository.product.ProductRepository;
import com.fundfun.fundfund.service.user.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final UserServiceImpl userService;

    @Override
    public Product createProduct() { //테스트용code
        Product product = Product.builder()
                .title("A+B")
                .crowdStart("2023-05-15")
                .crowdEnd("2023-07-15")
                .goal(1000L)
                .currentGoal(1500L)
                .status("진행중")
                .description("펀드진행중")
                .build();

        productRepository.save(product);
        return product;
    }

    public Product createProduct2() { //테스트용code
        Product product = Product.builder()
                .title("C+D")
                .crowdStart("2023-08-15")
                .crowdEnd("2023-12-15")
                .currentGoal(66L)
                .status("진행마감")
                .description("펀드진행중")
                .build();

        productRepository.save(product);
        return product;
    }


    public List<Product> selectAll() {
        return productRepository.findAll();
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

    public int updateProduct(Long cost, UUID productId) {
        Product dbProduct = selectById(productId);
        Long money = dbProduct.getCurrentGoal() + cost;

        dbProduct.setCurrentGoal(money);
        Product result = productRepository.save(dbProduct);

        if (result == null)
            return 0;
        return 1;
    }

    @Override
    public Product registerProduct(ProductDto productDto) {
        Product product = Product.builder()
                .title(productDto.getTitle())
                .goal(productDto.getGoal())
                .currentGoal(0L)
                .crowdStart(productDto.getCrowdStart())
                .crowdEnd(productDto.getCrowdEnd())
                .description(productDto.getDescription())
                .status("진행중")
                .build();
        Product result = productRepository.save(product);

        return result;
    }

    public List<Product> search(String title) {
        List<Product> productList = productRepository.findByTitleContaining(title);
        return productList;
    }
}

