package com.fundfun.fundfund.service.product;

import com.fundfun.fundfund.domain.product.Product;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    /**
     * (해당 유저에 해당하는 주문서 ..) 전체 검색
     */
    List<Product> selectAll();

    /**
     * 상품 등록
     */
    void insert(Product product);

  /**
   * 상품 업데이트
   * */
    Product update(Product product);

    /**
     * 상품 삭제
     * */
    void delete(UUID id);

    /**
     * 주문서 상세보기
     */
    Product selectById(UUID id);




}
