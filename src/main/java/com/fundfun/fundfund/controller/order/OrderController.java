package com.fundfun.fundfund.controller.order;

import com.fundfun.fundfund.domain.order.Orders;
import com.fundfun.fundfund.domain.product.Product;
import com.fundfun.fundfund.domain.user.Users;
import com.fundfun.fundfund.exception.UserNotFoundException;
import com.fundfun.fundfund.service.order.OrderService;
import com.fundfun.fundfund.dto.order.InvestDto;
import com.fundfun.fundfund.service.order.OrderServiceImpl;
import com.fundfun.fundfund.service.product.ProductService;
import com.fundfun.fundfund.service.product.ProductServiceImpl;
import com.fundfun.fundfund.service.user.UserService;
import com.fundfun.fundfund.service.user.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.nio.ByteBuffer;
import java.security.Principal;
import java.util.List;
import javax.validation.Valid;
import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final OrderServiceImpl orderService;
    private final ProductServiceImpl productService;
    private final UserService userService;

    /**
     * 상품 Detail 페이지 + 투자 금액 입력 폼 페이지
     * @param investDto
     * @return view
     * */
    @GetMapping("/form")
    public String showOrderForm(InvestDto investDto, Model model,  String encId) {
        System.out.println("encId = " + encId);
        UUID uuid = orderService.decEncId(encId);
        // 복호화된 uuid로 해당 product 가져오기
        Product product = productService.selectById(uuid);
        model.addAttribute("product", product);
        model.addAttribute("encId", encId);

        long crowdDeadLine = productService.crowdDeadline(product);
        model.addAttribute("deadline", crowdDeadLine);

        return "order/order_form";
    }

    /**
     * 투자
     * user가 입력한 투자금액(cost) 갱신하기
     * @param investDto, bindingResult
     * @return view
     */
    @PostMapping("/send/{encId}")
    public String orderFormSend(Principal principal, @Valid InvestDto investDto, BindingResult bindingResult, @PathVariable String encId) {
        if(bindingResult.hasErrors()) {
            return "order/order_form";
        }
        UUID uuid = orderService.decEncId(encId);
        productService.selectById(uuid);
        Optional<Users> ou = userService.findByEmail(principal.getName());
        if(!ou.isPresent()) {
            throw new UserNotFoundException();
        }
        Users logined = ou.get();
        productService.updateCost(investDto.getCost(), logined);

        return "redirect:/order/receipt";
    }

    /**
     * 투자금액(입력)처리 후 영수증 확인 페이지
     * @return view
     */
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

