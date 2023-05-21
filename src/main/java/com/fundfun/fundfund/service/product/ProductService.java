package com.fundfun.fundfund.service.product;

import com.fundfun.fundfund.domain.post.Post;
import com.fundfun.fundfund.domain.product.Product;
import com.fundfun.fundfund.domain.user.Users;
import com.fundfun.fundfund.dto.product.ProductDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    /**
     * (해당 유저에 해당하는 주문서 ..) 전체 검색
     */
    List<Product> selectAll();

  /**
   * 상품 업데이트
   * */
    void update(UUID id);

    /**
     * 상품 삭제
     * */
    void delete(UUID id);

    /**
     * 주문서 상세보기
     */
    Product selectById(UUID id);

    /**
     * 현재모금액 갱신
     * */
    int updateCost(Long cost, UUID productId);


    /**
     * 상품 등록
     * */
    Product registerProduct(ProductDto productDto, MultipartFile thumbnailImg);

    /**
     * 상품등록 test
     * */
    Product createProduct();

    /**
     * 제목으로 상품 검색
     * */
    List<Product> searchTitle(String title);

  /***
   * 아이디로 상품 검색
   */
//  List<Product> searchId(Users user);

}
