package com.fundfun.fundfund.repository.product;

import com.fundfun.fundfund.domain.product.Items;
import com.fundfun.fundfund.domain.product.Product;
import com.fundfun.fundfund.domain.product.Weight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface WeightRepository extends JpaRepository<Weight, Integer> {

    List<Weight> findByProductTitle(String productTitle);
}