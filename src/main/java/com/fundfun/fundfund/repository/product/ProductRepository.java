package com.fundfun.fundfund.repository.product;

import com.fundfun.fundfund.domain.product.Product;
import com.fundfun.fundfund.domain.user.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    List<Product> findByTitleContaining(String title);

    @Query(value = "select p from Product p order by p.createdAt desc")
    List<Product> findAll();

    @Query(value = "select p from Product p where p.status = ?1")
    List<Product> findByStatus(String status);

}
