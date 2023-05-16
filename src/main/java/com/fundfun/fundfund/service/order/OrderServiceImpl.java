package com.fundfun.fundfund.service.order;

import com.fundfun.fundfund.domain.order.Orders;
import com.fundfun.fundfund.repository.order.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

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
    public int createOrder(int cost) {
        Orders order = Orders.builder()
                .cost(cost)
                .build();
        Orders result = orderRepository.save(order);
        if(result == null) {
            return 0;
        }
        return 1;
    }

    @Override
    public void delete() {

    }
}
