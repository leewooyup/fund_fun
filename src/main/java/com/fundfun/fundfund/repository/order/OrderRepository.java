package com.fundfun.fundfund.repository.order;

import com.fundfun.fundfund.domain.order.Orders;
import org.hibernate.criterion.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Orders, UUID>{
}
