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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
//@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    @Value("${custom.genFileDirPath}")
    private String genFileDirPath;
    private final ProductRepository productRepository;
    private final OrderServiceImpl orderService;
    private final UserServiceImpl userService;

    @Override
    public Product createProduct() { //테스트용code
        Product product = Product.builder()
                .title("A+B")
                .crowdStart("2023-05-15")
                .crowdEnd("2023-05-21")
                .goal(1000L)
                .currentGoal(1500L)
                .status("진행중")
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
                .status("진행마감")
                .description("펀드진행중")
                .build();

        productRepository.save(product);
        return product;
    }


    public List<Product> selectAll() {
        return productRepository.findAll();
    }

    public Product update(Product product) {
        return productRepository.save(product);
    }

    public void delete(UUID id) {
        Product dbProduct = productRepository.findById(id).orElse(null);
        productRepository.delete(dbProduct);
    }

    public Product selectById(UUID id) {
        return productRepository.findById(id).orElse(null);
    }

    /**
     * user가 order_form에서 해당상품에 투자할 때마다, Product의 currentGoal upadate
     *
     * @param cost
     * @param productId
     * @return 성공(1)/실패(0)
     */
    @Transactional
    public int updateProduct(Long cost, UUID productId) throws RuntimeException {
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

    @Override
    public Product registerProduct(ProductDto productDto, MultipartFile thumbnailImg) {
        String thumbnailImgRelPath = saveThumbnailImg(thumbnailImg);
        System.out.println("thumbnailImgRelPath: " + thumbnailImgRelPath);
        Product product = Product.builder()
                .title(productDto.getTitle())
                .goal(productDto.getGoal())
                .currentGoal(0L)
                .crowdStart(productDto.getCrowdStart())
                .crowdEnd(productDto.getCrowdEnd())
                .description(productDto.getDescription())
                .status("진행중")
                .thumbnailRelPath(thumbnailImgRelPath)
                .build();
        Product result = productRepository.save(product);

        return result;
    }

    public List<Product> search(String title) {
        List<Product> productList = productRepository.findByTitleContaining(title);
        return productList;
    }
    public long crowdDeadline(Product product) {
        Date deadLine = product.toDate(product.getCrowdEnd());
        Date now = new Date();
        long diff = ((deadLine.getTime() - now.getTime())/(24*60*60*1000))+1;

        return diff;
    }


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

