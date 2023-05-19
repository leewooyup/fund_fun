package com.fundfun.fundfund.repository.product;

import com.fundfun.fundfund.domain.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    //@Query(value = "select p from Product p where p.title like '%title%'")
    List<Product> findByTitleContaining(String title);

    @Query(value = "select p from Product p order by p.createDate desc")
    List<Product> findAll();
}
