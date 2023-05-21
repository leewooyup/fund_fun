package com.fundfun.fundfund.service.product;

import com.fundfun.fundfund.domain.order.Orders;
import com.fundfun.fundfund.domain.product.Product;
import com.fundfun.fundfund.domain.user.Users;
import com.fundfun.fundfund.dto.product.ProductDto;
import com.fundfun.fundfund.repository.order.OrderRepository;
import com.fundfun.fundfund.repository.product.ProductRepository;
import com.fundfun.fundfund.service.order.OrderServiceImpl;
import com.fundfun.fundfund.service.user.UserServiceImpl;
import com.fundfun.fundfund.util.Util;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
//@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    @Value("${custom.genFileDirPath}")
    private String genFileDirPath;
    private final ProductRepository productRepository;
    private final OrderServiceImpl orderService;
    private final UserServiceImpl userService;
    private final ModelMapper modelMapper;

    @Override
    public Product createProduct() { //테스트용code
        Product product = Product.builder()
                .title("A+B")
                .crowdStart("2023-05-15")
                .crowdEnd("2023-05-21")
                .goal(1000L)
                .currentGoal(1500L)
                .status(0)
                .description("펀드진행중")
                .build();

        productRepository.save(product);
        return product;
    }

    public Product createProduct2() { //테스트용code
        Product product = Product.builder()
                .title("C+D")
                .crowdStart("2023-08-15")
                .crowdEnd("2023-12-15")
                .currentGoal(66L)
                .status(2)
                .description("펀드진행중")
                .build();

        productRepository.save(product);
        return product;
    }


    /**
     * 전체 상품 조회
     */
    public List<Product> selectAll() {
        List<Product> productList = productRepository.findAll();
//        List<ProductDto> product =
//                productList.stream().map(p -> modelMapper.map(p, ProductDto.class)).collect(Collectors.toList());
        return productList;
    }

    /**
     * 상품 업데이트 --> 디테일 정보에서 수정
     */
    public void update(UUID id) {
        productRepository.save(selectById(id));
    }

    /**
     * 상품 삭제
     */
    public void delete(UUID id) {
        productRepository.deleteById(id);
    }

    /**
     * id에 해당하는 상품 찾기
     */
    public Product selectById(UUID id) {
        return productRepository.findById(id).orElse(null);
    }

    /**
     * 상품 투자금 갱신
     *
     * @param cost
     * @param productId
     * @return 성공(1)/실패(0)
     */
    @Transactional
    public int updateCost(Long cost, UUID productId) throws RuntimeException {
        Product dbProduct = selectById(productId);
        //Product currentGoal 갱신하기
        Long money = dbProduct.getCurrentGoal() + cost;
        dbProduct.setCurrentGoal(money);
        //Order(주문서) 생성
        Users user = userService.createUser(); //테스트용 코드, 현재 로그인한 user
        Orders order = orderService.createOrder(cost, dbProduct, user);

        //User Point update
        //Security annotation으로 가져오기

        //하나라도 못찾은 것이 있다면, Rollback
        if (dbProduct == null || order == null || user == null) {
            throw new RuntimeException();
        }

        //다 성공했다면 update
        Product result = productRepository.save(dbProduct);

        if (result == null)
            return 0;
        return 1;
    }

    /**
     * 상품 등록하기
     */
    @Override
    public Product registerProduct(ProductDto productDto, MultipartFile thumbnailImg) {
        String thumbnailImgRelPath = saveThumbnailImg(thumbnailImg);

        Product product = modelMapper.map(productDto, Product.class);
        product.setThumbnailRelPath(thumbnailImgRelPath);

        return productRepository.save(product);
    }

    /**
     * 제목 키워드로 상품 조회
     */
    public List<Product> searchTitle(String title) {
        List<Product> productList = productRepository.findByTitleContaining(title);
        if (productList == null) {
            throw new RuntimeException("해당 게시물이 존재하지 않습니다.");
        }
//        List<ProductDto> product =
//                productList.stream().map(p -> modelMapper.map(p, ProductDto.class)).collect(Collectors.toList());
        return productList;
    }

    /**
     * 아이디로 상품 조회
     */
//    public List<Product> searchId(Users user) {
//        List<Product> productList = productRepository.findByUserId(user.getId());
//        if(productList == null){
//            throw new RuntimeException("해당 게시글이 존재하지 않습니다.");
//        }
//        return productList ;
//    }

    /**
     * 마감일까지의 d-day
     * */
    public long crowdDeadline(Product product) {
        Date deadLine = product.toDate(product.getCrowdEnd());
        Date now = new Date();
        long diff = ((deadLine.getTime() - now.getTime()) / (24 * 60 * 60 * 1000)) + 1;

        return diff;
    }


    /**
     * 썸네일
     * */
    public String getCurThumbnailImgDirName() {
        return "product/" + Util.date.getCurDateFormatted("yyyy_MM_dd");
    }

    public String saveThumbnailImg(MultipartFile thumbnailImg) {
        System.out.println("thumbnailImg: " + thumbnailImg);
        String thumbnailImgDirName = getCurThumbnailImgDirName();
        String fileName = UUID.randomUUID() + ".jpeg";
        String thumbnailImgDirPath = genFileDirPath + "/" + thumbnailImgDirName;
        String thumbnailImgFilePath = thumbnailImgDirPath + "/" + fileName;
        System.out.println("thumbnailImgFilePath: " + thumbnailImgFilePath);
        new File(thumbnailImgDirPath).mkdirs();

        try {
            thumbnailImg.transferTo(new File(thumbnailImgFilePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return thumbnailImgDirName + "/" + fileName;
    }
}

