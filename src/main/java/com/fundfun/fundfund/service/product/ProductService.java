package com.fundfun.fundfund.service.product;

import com.fundfun.fundfund.domain.product.Product;
import com.fundfun.fundfund.domain.user.UserDTO;
import com.fundfun.fundfund.dto.product.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    void update(UUID productId, ProductDto productDto, MultipartFile thumbnailImg, UserDTO userDTO);

    /**
     * 상품 삭제
     */
    void delete(UUID productId);

    /**
     * 주문서 상세보기
     */
    ProductDto selectById(UUID id);

    /**
     * 현재모금액 갱신
     */
    int updateCost(Long cost, ProductDto productDto, UserDTO userDTO);

    /**
     * 크라우드펀딘 종료, 상품 상태 변경
     */
    boolean updateStatus(ProductDto productDto);

    /**
     * 상품 등록
     */
    Product registerProduct(ProductDto productDto, MultipartFile thumbnailImg, UserDTO userDTO);

    /**
     * 제목으로 상품 검색
     */
    List<ProductDto> searchTitle(String title);

    /**
     * 아이디로 상품 검색
     */
    //List<Product> searchId(UserDTO userDTO);

    /**
     * 진행 상태(Status)로 상품 검색
     */
    List<ProductDto> selectByStatus(String status);

    /**
     * 마감일 설정
     */
    int crowdDeadline(ProductDto productDto);

//    /**
//     * 하드코딩,,create
//     */
//    ProductDto createProduct(Users users);

    /**
     * 페이징 처리
     * */
    Page<ProductDto> selectAll(Pageable pageable);
    Page<ProductDto> selectByStatus(Pageable pageable, String status);


}
