package com.fundfun.fundfund.service.product;

import com.fundfun.fundfund.domain.order.Orders;
import com.fundfun.fundfund.domain.product.Product;
import com.fundfun.fundfund.domain.user.UserDTO;
import com.fundfun.fundfund.domain.user.Users;
import com.fundfun.fundfund.dto.product.ProductDto;
import com.fundfun.fundfund.repository.product.ProductRepository;
import com.fundfun.fundfund.service.order.OrderServiceImpl;
import com.fundfun.fundfund.service.user.UserService;
import com.fundfun.fundfund.util.Util;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
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
    private final UserService userService;
    private final ModelMapper modelMapper;

//    @Override
//    public Product createProduct() { //테스트용code
//        Product product = Product.builder()
//                .title("A+B")
//                .crowdStart("2023-05-15")
//                .crowdEnd("2023-05-21")
//                .goal(1000L)
//                .currentGoal(1500L)
//                .status("진행중")
//                .description("펀드진행중")
//                .build();
//
//        productRepository.save(product);
//        return product;
//    }

    /**
     * 전체 상품 조회
     */
    public List<Product> selectAll() {
        List<Product> productList = productRepository.findAll();
        //return  productList.stream().map(product -> modelMapper.map(product, ProductDto.class)).collect(Collectors.toList());
        return productList;
    }

    /**
     * 상품 업데이트 --> 디테일 정보에서 수정
     */
    public void update(UUID productId, ProductDto productDto, MultipartFile thumbnailImg, Users user) {
        Product dbProduct = productRepository.findById(productId).orElse(null);

        if (dbProduct == null || user != dbProduct.getFundManager()) {
            throw new RuntimeException("상품을 수정할 수 없습니다.");
        }
        //정보 수정
        productDto.setId(productId);
        productDto.setCrowdStart(dbProduct.getCrowdStart());
        productDto.setCrowdEnd(dbProduct.getCrowdEnd());
        productDto.setFundManager(user);

        String thumbnailImgRelPath = saveThumbnailImg(thumbnailImg);
        productDto.setThumbnailRelPath(thumbnailImgRelPath);

        Product product = modelMapper.map(productDto, Product.class);
        productRepository.save(product);
    }

    /**
     * 상품 삭제
     */
    public void delete(UUID productId, Users user) {
        Product product = productRepository.findById(productId).orElse(null);
        if (product == null || user != product.getFundManager()) {
            throw new RuntimeException("상품을 삭제할 수 없습니다.");
        }
        productRepository.delete(product);
    }

    /**
     * id에 해당하는 상품 찾기
     */
    public ProductDto selectById(UUID id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            throw new RuntimeException("해당 상품이 존재하지 않습니다.");
        }
        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        return productDto;
    }

    /**
     * 상품 투자금 갱신
     * <p>
     * //     * @param cost
     * //     * @param productId
     *
     * @return 성공(1)/실패(0)
     */
    @Transactional
    public int updateCost(Long cost, ProductDto productDto, Users user) throws RuntimeException {
        //Product currentGoal 갱신하기
        Long money = productDto.getCurrentGoal() + cost;
        productDto.setCurrentGoal(money);

        //Order(주문서) 생성
        Orders order = orderService.createOrder(cost, productDto, user); //(유저의 투자금액, 상품 정보, 로그인한 유저 정보)
        System.out.println("order 정보는!!!!!! "+ order.getId());
        //User Point update
        userService.updateMoney(user.getMoney() - cost, user);

        //하나라도 못찾은 것이 있다면, Rollback
        if (productDto == null || order == null || user == null) {
            throw new RuntimeException("업데이트에 실패하셨습니다.");
        }

        //다 성공했다면 update
        Product result = productRepository.save(modelMapper.map(productDto, Product.class));
        if (result == null)
            return 0;
        return 1;
    }


    /**
     * 상품 등록하기
     */
    @Override
    public Product registerProduct(ProductDto productDto, MultipartFile thumbnailImg, Users user) {
        String thumbnailImgRelPath = saveThumbnailImg(thumbnailImg);

        productDto.setFundManager(user);
        productDto.setThumbnailRelPath(thumbnailImgRelPath);
        Product product = modelMapper.map(productDto, Product.class);

        return productRepository.save(product);
    }

    /**
     * 제목 키워드로 상품 조회
     */
    public List<Product> searchTitle(String title) {
        List<Product> productList = productRepository.findByTitleContaining(title);
//        if (productList == null) {
//            throw new RuntimeException("해당 상품이 존재하지 않습니다.");
//        }
//        return productList.stream().map(product -> modelMapper.map(product, ProductDto.class)).collect(Collectors.toList());
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

//    public List<Product> selectByStatus(int status){
//        return productRepository.findByStatus(status);
//    }

    /**
     * 상품리스트 status(= 진행중 or 완료)에 따른 페이지 설정
     * */
    public List<Product> selectByStatus(String status){
        return productRepository.findByStatus(status);
    }

    /**
     * 마감일까지의 d-day
     */
    public long crowdDeadline(ProductDto productDto) {
        Date deadLine = productDto.toDate(productDto.getCrowdEnd());
        Date now = new Date();
        long diff = ((deadLine.getTime() - now.getTime()) / (24 * 60 * 60 * 1000) + 1);

        return diff;
    }


    /**
     * 썸네일
     */
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

