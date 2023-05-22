package com.fundfun.fundfund.service.order;

import com.fundfun.fundfund.domain.order.Orders;
import com.fundfun.fundfund.domain.product.Product;
import com.fundfun.fundfund.domain.user.Users;
import com.fundfun.fundfund.dto.order.InvestDto;
import com.fundfun.fundfund.dto.product.ProductDto;

import java.util.List;
import java.util.UUID;

public interface OrderService {

    /**
     * 전체 검색
     * */
    List<Orders> selectAll();
    
     /* 주문 등록
     * */
    Orders createOrder(InvestDto orderDto, ProductDto productDto, Users user);

    /**
     * 주문삭제
     * */
    void delete(UUID orderId, Users user);

    /**
     * 총 주문금액
     * */
    int getCurrentCollection(ProductDto productDto);
}
