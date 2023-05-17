package com.fundfun.fundfund.controller.order;

import com.fundfun.fundfund.domain.order.Orders;
import com.fundfun.fundfund.domain.product.Product;
import com.fundfun.fundfund.service.order.OrderService;
import com.fundfun.fundfund.service.order.OrderServiceImpl;
import com.fundfun.fundfund.service.product.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final OrderServiceImpl orderService;
    private final ProductServiceImpl productService;

    /**
     * 전체 검색
     * */
    @GetMapping("/form")
    public String showOrderForm() {
        return "order/order_form";
    }

    /**
     * user가 입력한 투자금액(cost) 갱신하기
     * @param cost
     * @return view
     */
    @PostMapping("/send")
    public String orderFormSend(int cost) {
        Product product = productService.createProduct();
        Orders order = orderService.createOrder(cost, product);
        System.out.println("order.getProduct().getId(): " + order.getProduct().getId());
        productService.updateProduct(cost, order.getProduct().getId());

        return "redirect:/order/receipt";
    }

    @GetMapping("/receipt")
    public String showOrderReceipt(Model model) {
        Product product = productService.createProduct();
        int curCollect = productService.getCurrentCollection(product);
        model.addAttribute("curCollect", curCollect);
        return "order/order_receipt";
    }



    /**
     * 등록
     * */

    /**
     * 상세보기
     * */

    /**
     * 주문 취소 -> 삭제
     */
    @RequestMapping("/delete")
    public String delete(UUID id, String password) {


        return "redirect:/order/list";

    }
}

