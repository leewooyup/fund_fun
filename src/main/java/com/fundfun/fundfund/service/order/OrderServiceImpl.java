package com.fundfun.fundfund.service.order;

import com.fundfun.fundfund.domain.order.Orders;
import com.fundfun.fundfund.domain.product.Product;
import com.fundfun.fundfund.domain.user.UserDTO;
import com.fundfun.fundfund.domain.user.Users;
import com.fundfun.fundfund.dto.order.InvestDto;
import com.fundfun.fundfund.dto.product.ProductDto;
import com.fundfun.fundfund.repository.order.OrderRepository;
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
    public List<Orders> selectByUser(UUID userId) {
        return orderRepository.findByUserId(userId);
    }

    //주문 업데이트
    public void update(InvestDto investDto){
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
        System.out.println("uuidString: " + uuidString);
        return UUID.fromString(uuidString);
    }

    //주문등록
    public Orders createOrder(Long cost, ProductDto productDto, Users user) {
        InvestDto investDto = new InvestDto();
        Product product = modelMapper.map(productDto, Product.class);

        investDto.setProduct(product);
        investDto.setUser(user);
        investDto.setCost(cost);

        Orders order = modelMapper.map(investDto, Orders.class);
        if(order == null){
            throw new RuntimeException("투자에 실패하셨습니다.");
        }
        return orderRepository.save(order);
    }

    //주문삭제
    @Override
    public void delete(UUID orderId, Users user) {
        Orders order = orderRepository.findById(orderId).orElse(null);
        if (order == null || user != order.getUser()) {
            throw new RuntimeException("해당 투자를 삭제할 수 없습니다.");
        }
        orderRepository.delete(order);
    }

}
