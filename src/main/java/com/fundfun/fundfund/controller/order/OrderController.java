package com.fundfun.fundfund.controller.order;

import com.fundfun.fundfund.domain.order.Orders;
import com.fundfun.fundfund.domain.product.Product;
import com.fundfun.fundfund.dto.order.InvestDto;
import com.fundfun.fundfund.service.order.OrderServiceImpl;
import com.fundfun.fundfund.service.product.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    /**
     * 상품 Detail 페이지 + 투자 금액 입력 폼 페이지
     * @param investDto
     * @return view
     * */
    @GetMapping("/form")
    public String showOrderForm(InvestDto investDto, Model model) {
        Product product = productService.createProduct(); //테스트용 코드
        //UUID encode
        Base64.Encoder encoder = Base64.getEncoder();
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        long bits = product.getId().getMostSignificantBits();
        byte[] encodedBits = encoder.encode(bb.array());
        System.out.println("encodedBits: " + encodedBits);
        model.addAttribute("encodedBits", encodedBits);
        return "order/order_form";
    }

    /**
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
        Orders order = orderService.createOrder(investDto.getCost(), product);
        System.out.println("order.getProduct().getId(): " + order.getProduct().getId());
        productService.updateProduct(investDto.getCost(), order.getProduct().getId());

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

