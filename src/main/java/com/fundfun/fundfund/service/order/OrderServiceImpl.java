package com.fundfun.fundfund.service.order;

import com.fundfun.fundfund.domain.order.Orders;
import com.fundfun.fundfund.domain.product.Product;
import com.fundfun.fundfund.domain.user.Users;
import com.fundfun.fundfund.repository.order.OrderRepository;
import com.fundfun.fundfund.service.product.ProductServiceImpl;
import com.fundfun.fundfund.service.user.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Base64;
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

//    public Orders createOrder(int cost) {
//        Orders order = Orders.builder()
//                .cost(cost)
//                .build();
//        return orderRepository.save(order);
//    }

    public UUID decEncId(String encId) {
        // UUID 디코딩
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] decodedUUIDBytes  = decoder.decode(encId);
        String uuidString = new String(decodedUUIDBytes);
        System.out.println("uuidString: " + uuidString);
        return UUID.fromString(uuidString);
    }
    public Orders createOrder(Long cost, Product product, Users user) {
        Orders order = new Orders();
        order.linkProduct(product);
        order.linkUser(user);
        order.setCost(cost);
        return orderRepository.save(order);
    }


    @Override
    public void delete() {

    }

    @Override
    public int getCurrentCollection(Product product) {
        Users user = new Users(); //일단 로그인한 유저의 정보 있다고 가정


        return 0;
    }
}
