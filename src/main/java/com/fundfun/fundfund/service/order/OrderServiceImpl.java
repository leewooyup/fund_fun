package com.fundfun.fundfund.service.order;

import com.fundfun.fundfund.domain.order.Orders;
import com.fundfun.fundfund.domain.product.Product;
import com.fundfun.fundfund.domain.user.Users;
import com.fundfun.fundfund.dto.order.InvestDto;
import com.fundfun.fundfund.dto.product.ProductDto;
import com.fundfun.fundfund.repository.order.OrderRepository;
import com.fundfun.fundfund.service.product.ProductServiceImpl;
import com.fundfun.fundfund.service.user.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<Orders> selectAll() {

        return orderRepository.findAll();
    }

    /**
     * UUID 디코딩
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

    public Orders createOrder(InvestDto orderDto, ProductDto productDto, Users user) {
        Product product = modelMapper.map(productDto, Product.class);
        orderDto.setProduct(product);
        orderDto.setUser(user);

        Orders order = modelMapper.map(orderDto, Orders.class);
        return orderRepository.save(order);
    }

    @Override
    public void delete(UUID orderId, Users user) {
        Orders order = orderRepository.findById(orderId).orElse(null);
        if(order == null || user != order.getUser()){
            throw new RuntimeException("주문을 삭제할 수 없습니다.");
        }
        orderRepository.delete(order);
    }

    //test 코드
    @Override
    public int getCurrentCollection(ProductDto productDto) {
        Users user = new Users(); //일단 로그인한 유저의 정보 있다고 가정
        return 0;
    }
}
