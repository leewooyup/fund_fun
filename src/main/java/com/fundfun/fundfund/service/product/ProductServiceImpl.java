package com.fundfun.fundfund.service.product;

import com.fundfun.fundfund.domain.order.Orders;
import com.fundfun.fundfund.domain.product.Product;
import com.fundfun.fundfund.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public List<Product> selectAll() {
        return productRepository.findAll();
    }

    public void insert(Product product ){
        productRepository.save(product);
    }

    public Product update(Product product){
        return productRepository.save(product);
    }

    public void delete(UUID id){
        Product dbProduct = productRepository.findById(id).orElse(null);
        productRepository.delete(dbProduct);
    }

    public Product selectById(UUID id){
        return  productRepository.findById(id).orElse(null);
    }
}

