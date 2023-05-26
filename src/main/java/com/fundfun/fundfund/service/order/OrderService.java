package com.fundfun.fundfund.service.order;

import com.fundfun.fundfund.domain.order.Orders;
import com.fundfun.fundfund.domain.product.Product;
import com.fundfun.fundfund.domain.user.UserDTO;
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

    /**
     * 주문 조회
     * */
    Orders selectById(UUID id);

    /**
     * product와 연관된 주문 찾기
     */
    List<Orders> selectByProductId(UUID productId);

    /**
     * user와 연관된 주문 찾기
     */
    List<Orders> selectByUserId(UUID userId);

    /**
     * 주문 등록
     * */
    Orders createOrder(Long cost, UUID productId, UUID userId);

    /**
     * 주문 업데이트
     * */
    void update(InvestDto investDto);

    /**
     * 주문삭제
     * */
    void delete(UUID orderId);


    /**
     * UUID 디코딩
     * */
    UUID decEncId(String encId);
}