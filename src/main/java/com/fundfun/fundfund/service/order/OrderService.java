package com.fundfun.fundfund.service.order;

import com.fundfun.fundfund.domain.order.Orders;
import com.fundfun.fundfund.domain.product.Product;
import com.fundfun.fundfund.domain.user.Users;

import java.util.List;

public interface OrderService {

    /**
     * 전체 검색
     * */
    List<Orders> selectAll();
    
     /* 주문 등록
     * */
    Orders createOrder(Long cost, Product product, Users user);

    /**
     * 주문삭제
     * */
    void delete();

    /**
     * 총 주문금액
     * */
    int getCurrentCollection(Product product);
}
