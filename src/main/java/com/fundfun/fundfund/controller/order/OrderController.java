package com.fundfun.fundfund.controller.order;

import com.fundfun.fundfund.domain.order.Orders;
import com.fundfun.fundfund.domain.product.Product;
import com.fundfun.fundfund.domain.user.Users;
import com.fundfun.fundfund.service.order.OrderService;
import com.fundfun.fundfund.service.order.OrderServiceImpl;
import com.fundfun.fundfund.service.product.ProductServiceImpl;
import com.fundfun.fundfund.service.user.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final OrderServiceImpl orderService;
    private final ProductServiceImpl productService;
    private final UserServiceImpl userService;

    /**
     * 전체 검색
     * */
    @GetMapping("/form")
    public String showOrderForm() {
        return "order/order_form";
    }

    /**
     * 투자
     * user가 입력한 투자금액(cost) 갱신하기
     * @param cost
     * @return view
     */
//    @PostMapping("/send")
//    public String orderFormSend(Long cost) {
//        Product product = productService.createProduct();
//        Users users = userService.createUser();
//        Orders order = orderService.createOrder(cost, product, users);
//
//        productService.updateProduct(cost, order.getProduct().getId());
//
//        return "redirect:/order/receipt";
//    }


    /**
     * 내가 투자한 금액 가져와서 전체 투자금액 갱신
     * post -> int
     * */
//    @GetMapping("/receipt")
//    public String showOrderReceipt(Model model) {
//        Product product = productService.createProduct();
//        int curCollect = orderService.getCurrentCollection(product);
//        model.addAttribute("curCollect", curCollect);
//        return "order/order_receipt";
//    }

    /**
     * 마이페이지에서 주문 상세보기
     * */

    /**
     * 주문 취소 -> 삭제
     */
    @RequestMapping("/delete")
    public String delete(UUID id, String password) {


        return "redirect:/order/list";

    }


}

