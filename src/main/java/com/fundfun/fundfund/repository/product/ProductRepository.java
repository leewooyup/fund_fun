package com.fundfun.fundfund.repository.product;

import com.fundfun.fundfund.domain.product.Product;
import com.fundfun.fundfund.domain.user.Users;
import com.fundfun.fundfund.dto.product.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    List<Product> findByTitleContaining(String title);
    @Query(value = "select p from Product p order by p.createdAt desc")
    List<Product> findAll();

    @Query("select p from Product p order by p.currentGoal desc, p.createdAt desc")
    List<Product> findByCurrentGoal();


    @Query(value = "select p from Product p where p.status = ?1")
    List<Product> findByStatus(String status);

    @Query(value = "select p from Product p where p.status = ?1")
    Page<Product> findByStatus(Pageable pageable, String status);

}