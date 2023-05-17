package com.fundfun.fundfund.service.order;

import com.fundfun.fundfund.domain.order.Orders;
import com.fundfun.fundfund.domain.product.Product;
import com.fundfun.fundfund.repository.order.OrderRepository;
import com.fundfun.fundfund.service.product.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

        @Override
    public List<Orders> selectAll() {
        return null;
    }

    @Override
    public void insert(Orders order) {

    }

    @Override
    public Orders createOrder(int cost) {
        Orders order = Orders.builder()
                .cost(cost)
                .build();
        return orderRepository.save(order);
    }

    public Orders createOrder(int cost, Product product) {
        Orders order = Orders.builder()
                .cost(cost)
                .product(product)
                .build();
        return orderRepository.save(order);
    }

    @Override
    public void delete() {

    }
}
