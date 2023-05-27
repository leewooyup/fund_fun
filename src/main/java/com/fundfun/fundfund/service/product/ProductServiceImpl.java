package com.fundfun.fundfund.service.product;

import com.fundfun.fundfund.domain.order.Orders;
import com.fundfun.fundfund.domain.product.Items;
import com.fundfun.fundfund.domain.product.Product;
import com.fundfun.fundfund.domain.product.Weight;
import com.fundfun.fundfund.domain.user.UserDTO;
import com.fundfun.fundfund.domain.user.Users;
import com.fundfun.fundfund.dto.product.ProductDto;
import com.fundfun.fundfund.exception.InSufficientMoneyException;
import com.fundfun.fundfund.repository.order.OrderRepository;
import com.fundfun.fundfund.repository.product.ItemsRepository;
import com.fundfun.fundfund.repository.product.ProductRepository;
import com.fundfun.fundfund.repository.product.WeightRepository;
import com.fundfun.fundfund.service.order.OrderServiceImpl;
import com.fundfun.fundfund.service.user.UserService;
import com.fundfun.fundfund.util.Util;
import jdk.swing.interop.LightweightContentWrapper;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.asm.Advice;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
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
    private final ItemsRepository itemsRepository;
    private final WeightRepository weightRepository;
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    private ProductDto productDto;

//    public ProductDto createProduct(Users users) { //테스트용code
//
//        LocalDate startDate = LocalDate.parse("2023-05-24");
//        LocalDate endDate = LocalDate.parse("2023-05-29");
//        Product product = Product.builder()
//                .title("A+B")
//                .crowdStart(startDate.toString())
//                .crowdEnd("2023-05-23")
//                .goal(1000000L)
//                .currentGoal(500L)
//                .status("진행중")
//                .description("펀드진행중")
//                .fundManager(users)
//                .build();
//
//        productRepository.save(product);
//
//        ProductDto productDto = modelMapper.map(product, ProductDto.class);
//        return productDto;
//    }

    /**
     * 전체 상품 조회
     */
    public List<ProductDto> selectAll() {
        List<Product> productList = productRepository.findAll();
        return productList.stream().map(product -> modelMapper.map(product, ProductDto.class)).collect(Collectors.toList());
    }

    /**
     * 상품 업데이트 --> 디테일 정보에서 수정
     */
    public void update(UUID productId, ProductDto productDto, MultipartFile thumbnailImg, UserDTO userDTO) {
        Product dbProduct = productRepository.findById(productId).orElse(null);
        Users user = modelMapper.map(userDTO, Users.class);

        if (dbProduct == null || userDTO.equals(dbProduct.getFundManager())) {
            throw new RuntimeException("상품을 수정할 수 없습니다.");
        }

        //정보 수정
        productDto.setId(productId);
        productDto.setCrowdStart(dbProduct.getCrowdStart());
        productDto.setCrowdEnd(dbProduct.getCrowdEnd());
        productDto.setFundManager(user);
        productDto.setStatus(dbProduct.getStatus());
        productDto.setCurrentGoal(dbProduct.getCurrentGoal());

        String thumbnailImgRelPath = null;
        if (thumbnailImg.isEmpty()) {
            thumbnailImgRelPath = "product/avatar.jpg";
            System.out.println("thumbnailImgRelPath: " + thumbnailImgRelPath);
        } else {
            thumbnailImgRelPath = saveThumbnailImg(thumbnailImg);
        }
        productDto.setThumbnailRelPath(thumbnailImgRelPath);
        Product product = modelMapper.map(productDto, Product.class);

        productRepository.save(product);
    }

    /**
     * 상품 삭제
     */
    public void delete(UUID productId) {
        Product product = productRepository.findById(productId).orElse(null);
        List<Orders> orderList = orderRepository.findByProductId(productId);

        if (product == null || !orderList.isEmpty()) {
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
     * //     * @param cost
     * //     * @param productId
     *
     * @return 성공(1)/실패(0)
     */
    @Transactional
    public int updateCost(Long cost, ProductDto productDto, UserDTO userDTO) {
        //Product currentGoal 갱신하기
        Long money = productDto.getCurrentGoal() + cost;
        productDto.setCurrentGoal(money);

        Product result = productRepository.save(modelMapper.map(productDto, Product.class));
        if (result == null)
            return 0;
        return 1;
    }


    /**
     * 매일 자정 펀딩 상태 체크 및 update
     *
     * @param productDto
     * @return update(true)/not(false)
     */
    @Override
    public boolean updateStatus(ProductDto productDto) {
        String deadline = productDto.getCrowdEnd();
        LocalDate deadlineLD = LocalDate.parse(deadline);
        // deadline이 오늘날짜보다 작을 경우
        if (deadlineLD.isBefore(LocalDate.now())) {
            productDto.setStatus("진행종료");
            Product product = modelMapper.map(productDto, Product.class);
            productRepository.save(product);
            return true;
        }
        return false;
    }

    /**
     * 상품 등록하기
     */
    @Override
    public ProductDto registerProduct(ProductDto productDto, MultipartFile thumbnailImg, UserDTO userDTO) {
        System.out.println("thumbnailImg: " + thumbnailImg);
        String thumbnailImgRelPath = null;
        if (thumbnailImg.isEmpty()) {
            thumbnailImgRelPath = "product/avatar.jpg";
            System.out.println("thumbnailImgRelPath: " + thumbnailImgRelPath);
        } else {
            thumbnailImgRelPath = saveThumbnailImg(thumbnailImg);
        }
        Users user = modelMapper.map(userDTO, Users.class);
        productDto.setFundManager(user);
        String crowdEnd = plus2Weeks(productDto.getCrowdStart());
        productDto.setCrowdEnd(crowdEnd);
        productDto.setThumbnailRelPath(thumbnailImgRelPath);
        Product product = modelMapper.map(productDto, Product.class);

        if (product == null) {
            throw new RuntimeException("상품 등록에 실패하셨습니다.");
        }
        productRepository.save(product);
        return productDto;
    }

    /**
     * 제목 키워드로 상품 조회
     */
    public List<ProductDto> searchTitle(String title) {
        List<Product> productList = productRepository.findByTitleContaining(title);
        if (productList == null) {
            throw new RuntimeException("해당 상품이 존재하지 않습니다.");
        }
        return productList.stream().map(product -> modelMapper.map(product, ProductDto.class)).collect(Collectors.toList());
    }

    /**
     * 상품리스트 status(= 진행중 or 완료)에 따른 페이지 설정
     */
    public List<ProductDto> selectByStatus(String status) {
        List<Product> productList = productRepository.findByStatus(status);
        return productList.stream().map(product -> modelMapper.map(product, ProductDto.class)).collect(Collectors.toList());
    }

    public String plus2Weeks(String startDate) {
        LocalDate ld = LocalDate.parse(startDate);
        LocalDate crowdEnd = ld.plusDays(14);
        return crowdEnd.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    /**
     * 마감일까지의 d-day
     */
    public int crowdDeadline(ProductDto productDto) {
        LocalDate now = LocalDate.now();
        LocalDate deadLine = LocalDate.parse(productDto.getCrowdEnd());
        Period period = Period.between(now, deadLine);
//        System.out.println("gap: " + period.getDays());
        return period.getDays();
    }


    /**
     * 썸네일
     */
    public String getCurThumbnailImgDirName() {
        return "product/" + Util.date.getCurDateFormatted("yyyy_MM_dd");
    }

    public String saveThumbnailImg(MultipartFile thumbnailImg) {
        //System.out.println("thumbnailImg: " + thumbnailImg);
        String thumbnailImgDirName = getCurThumbnailImgDirName();
        String fileName = UUID.randomUUID() + ".jpeg";
        String thumbnailImgDirPath = genFileDirPath + "/" + thumbnailImgDirName;
        String thumbnailImgFilePath = thumbnailImgDirPath + "/" + fileName;
        //System.out.println("thumbnailImgFilePath: " + thumbnailImgFilePath);
        new File(thumbnailImgDirPath).mkdirs();

        try {
            thumbnailImg.transferTo(new File(thumbnailImgFilePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return thumbnailImgDirName + "/" + fileName;
    }

    /**
     * 페이징 처리
     */
    public Page<ProductDto> selectAll(Pageable pageable) {
        Page<Product> productList = productRepository.findAll(pageable);
        Page<ProductDto> productDtoList = productList.map(product -> modelMapper.map(product, ProductDto.class));

        return productDtoList;
    }

    @Override
    public Page<ProductDto> selectByStatus(Pageable pageable, String status) {
        Page<Product> productList = productRepository.findByStatus(pageable, status);
        Page<ProductDto> productDtoList = productList.map(product -> modelMapper.map(product, ProductDto.class));
        return productDtoList;
    }

    @Override
    public void createItems(String itemsName, ProductDto productDto) {
        Items items = new Items();
        items.setItemsName(itemsName);
        items.setProductTitle(productDto.getTitle());
        itemsRepository.save(items);
    }

    @Override
    public void createWeight(Integer weight, ProductDto productDto) {
        Weight w = new Weight();
        w.setWeight(weight);
        w.setProductTitle(productDto.getTitle());
        weightRepository.save(w);
    }

    @Override
    public List<Items> selectItemsByProductTitle(String productTitle) {
        return itemsRepository.findByProductTitle(productTitle);
    }

    @Override
    public List<Weight> selectWeightsByProductTitle(String productTitle) {
        return weightRepository.findByProductTitle(productTitle);
    }
    
    public List<ProductDto> selectByCurrentGoal() {
        List<Product> productList = productRepository.findByCurrentGoal();
        List<ProductDto> productDtoList = productList.stream().map(product -> modelMapper.map(product, ProductDto.class)).collect(Collectors.toList());
        return productDtoList;
    }
}
