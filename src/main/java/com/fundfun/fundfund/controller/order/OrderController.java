package com.fundfun.fundfund.controller.order;

import com.fundfun.fundfund.domain.order.Orders;
import com.fundfun.fundfund.domain.product.Product;
import com.fundfun.fundfund.domain.user.Users;
import com.fundfun.fundfund.service.order.OrderService;
import com.fundfun.fundfund.dto.order.InvestDto;
import com.fundfun.fundfund.service.order.OrderServiceImpl;
import com.fundfun.fundfund.service.product.ProductServiceImpl;
import com.fundfun.fundfund.service.user.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.nio.ByteBuffer;
import java.util.List;
import javax.validation.Valid;
import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final OrderServiceImpl orderService;
    private final ProductServiceImpl productService;
    private final UserServiceImpl userService;

    /**
     * 상품 Detail 페이지 + 투자 금액 입력 폼 페이지
     * @param investDto
     * @return view
     * */
    @PostMapping("/form")
    public String showOrderForm(InvestDto investDto, Model model,  String encId) {
        System.out.println("encId = " + encId);
        // UUID 복호화
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] decodedUUIDBytes  = decoder.decode(encId);
        String uuidString = new String(decodedUUIDBytes);
        UUID uuid = UUID.fromString(uuidString);
        // 복호화된 uuid로 해당 product 가져오기
        Product product = productService.selectById(uuid);
        System.out.println("product.getId(): " + product.getId());
        model.addAttribute("product", product);
        return "order/order_form";
    }

    /**
     * 투자
     * user가 입력한 투자금액(cost) 갱신하기
     * @param investDto, bindingResult
     * @return view
     */

    @PostMapping("/send/{encodedBits}")
    public String orderFormSend(@Valid InvestDto investDto, BindingResult bindingResult, @PathVariable String encodedBits) {
        if(bindingResult.hasErrors()) {
            return "order/order_form";
        }
        Product product = productService.createProduct();
        Users users = userService.createUser();
        Orders order = orderService.createOrder(investDto.getCost(), product, users);
        System.out.println("order.getProduct().getId(): " + order.getProduct().getId());
        productService.updateProduct(investDto.getCost(), order.getProduct().getId());

        return "redirect:/order/receipt";
    }

    @GetMapping("/receipt")
    public String showOrderReceipt(Model model) {
        Product product = productService.createProduct();
        int curCollect = orderService.getCurrentCollection(product);
        model.addAttribute("curCollect", curCollect);
        return "order/order_receipt";
    }



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

