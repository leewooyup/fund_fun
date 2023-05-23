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
    List<ProductDto> selectAll();

    /**
     * 상품 업데이트
     */
    void update(UUID productId, ProductDto productDto, MultipartFile thumbnailImg, Users user);

    /**
     * 상품 삭제
     */
    void delete(UUID productId, Users user);

    /**
     * 주문서 상세보기
     */
    ProductDto selectById(UUID id);

    /**
     * 현재모금액 갱신
     */
    int updateCost(Long cost, ProductDto productDto, Users user);



    /**
     * 상품 등록
     */
    Product registerProduct(ProductDto productDto, MultipartFile thumbnailImg, Users user);

    /**
     * 제목으로 상품 검색
     */
    List<ProductDto> searchTitle(String title);

    /**
     * 아이디로 상품 검색
     */
    //List<Product> searchId(Users user);

    /**
     * 진행 상태(Status)로 상품 검색
     */
    List<ProductDto> selectByStatus(String status);

    /**
     * 마감일 설정
     */
    int crowdDeadline(ProductDto productDto);

    /**
     * 하드코딩,,create
     */
    ProductDto createProduct(Users users);

}
