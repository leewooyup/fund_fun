package com.fundfun.fundfund.service.order;

import com.fundfun.fundfund.domain.order.Orders;

import java.util.List;
import java.util.UUID;

public interface OrderService {

    /**
     * 전체 검색
     * */
    List<Orders> selectAll();

    /**
     * 주문 등록
     * */
    void insert(Orders order);

    /**
     * 주문 수정
     * */
    void update(Orders order);

    /**
     * 주문삭제
     * */
    void delete();
}
