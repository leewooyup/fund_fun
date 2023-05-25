package com.fundfun.fundfund.service.order;

import com.fundfun.fundfund.domain.order.Orders;
import com.fundfun.fundfund.domain.product.Product;
import com.fundfun.fundfund.domain.user.UserAdapter;
import com.fundfun.fundfund.domain.user.UserDTO;
import com.fundfun.fundfund.domain.user.Users;
import com.fundfun.fundfund.dto.order.InvestDto;
import com.fundfun.fundfund.dto.product.ProductDto;
import com.fundfun.fundfund.repository.order.OrderRepository;
import com.fundfun.fundfund.repository.product.ProductRepository;
import com.fundfun.fundfund.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    //전체 조회
    @Override
    public List<Orders> selectAll() {

        return orderRepository.findAll();
    }

    //id로 전체 조회
    public Orders selectById(UUID orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }

    //상품(product)과 연관된 주문 전체 조회
    public List<Orders> selectByProductId(UUID productID) {
        return orderRepository.findByProductId(productID);
    }

    //유저(user)와 연관된 주문 전체 조회
    public List<Orders> selectByUserId(UUID userId) {
        return orderRepository.findByUserId(userId);
    }

    //주문 업데이트
    public void update(InvestDto investDto) {
        Orders order = modelMapper.map(investDto, Orders.class);
        orderRepository.save(order);

    }

    /**
     * UUID 디코딩
     *
     * @param encId
     * @return UUID
     */
    public UUID decEncId(String encId) {
        // UUID 디코딩
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] decodedUUIDBytes = decoder.decode(encId);
        String uuidString = new String(decodedUUIDBytes);
        //System.out.println("uuidString: " + uuidString);
        return UUID.fromString(uuidString);
    }

    //주문등록
    public Orders createOrder(Long cost, UUID productId, UUID userId) {
        InvestDto investDto = new InvestDto();
        Product product = productRepository.findById(productId).orElse(null);
        Users user = userRepository.findById(userId).orElse(null);
        System.out.println("user = " + userId + "user의 아이디 + " + user.getId());

        if(product == null){
            throw new RuntimeException("상품이 존재하지 않습니다.");
        }
        else if(user == null){
            throw new RuntimeException("투자자가 존재하지 않습니다.");
        }

        investDto.setProduct(product);
        investDto.setUser(user);
        investDto.setCost(cost);

        //투자 후 유저 충전금 업데이트
        user.minusMoney(cost);

        userRepository.save(user);
        System.out.println("머니 업데이트하고 겟 롤 " + user.getRole());


        Orders order = modelMapper.map(investDto, Orders.class);
        return orderRepository.save(order);
    }

    //주문삭제
    @Override
    public void delete(UUID orderId) {
        Orders order = orderRepository.findById(orderId).orElse(null);
        orderRepository.delete(order);
    }

//    //scheduler test 코드
//    public void sayHello() {
//        System.out.println("HELLO!!!!!!!!!!");
//    }

}
