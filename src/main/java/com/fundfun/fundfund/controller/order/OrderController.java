package com.fundfun.fundfund.controller.order;

import com.fundfun.fundfund.domain.user.Users;
import com.fundfun.fundfund.dto.order.InvestDto;
import com.fundfun.fundfund.dto.product.ProductDto;
import com.fundfun.fundfund.service.order.OrderServiceImpl;
import com.fundfun.fundfund.service.product.ProductServiceImpl;
import com.fundfun.fundfund.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.security.Principal;
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
     *
     * @param investDto
     * @return view
     */
    @GetMapping("/form")
    public String showOrderForm(InvestDto investDto, Model model, String encId) {
        UUID uuid = orderService.decEncId(encId);//productId
        // 복호화된 uuid로 해당 product 가져오기
        ProductDto productDto = productService.selectById(uuid);
        model.addAttribute("product", productDto);
        model.addAttribute("encId", encId);

        long crowdDeadLine = productService.crowdDeadline(productDto);
        model.addAttribute("deadline", crowdDeadLine);

        return "order/order_form";
    }

    /**
     * 투자하기
     * user가 입력한 투자금액(cost) 갱신하기
     *
     * @param investDto, bindingResult
     * @return view
     */

    @PostMapping("/send")
    public String orderFormSend(@Valid InvestDto investDto, BindingResult bindingResult, String encId, Model model) {
        if (bindingResult.hasErrors()) {
            return "order/order_form";
        }
        UUID productId = orderService.decEncId(encId);
        ProductDto productDto = productService.selectById(productId); //현재 product의 정보 가져오기

        if (productDto == null || investDto == null) {
            throw new RuntimeException("투자에 실패하셨습니다.");
        }
        model.addAttribute("product", productDto);
        model.addAttribute("invest", investDto);
        model.addAttribute("encId", encId);

        return "order/order_receipt";
    }

    /**
     * 주문 생성 + 상품 모금액 업데이트
     */
    @PostMapping("/update/{encId}")
    public String update(@PathVariable String encId, Long cost, Principal principal) {
        Users user= userService.findByEmail(principal.getName()).orElse(null);
        ProductDto productDto = productService.selectById(orderService.decEncId(encId));
        productService.updateCost(cost, productDto, user); //투자정보 갱신 + 주문서 만들기

        return "redirect:/product/list";
}

    /**
     * 투자금액(입력)처리 후 영수증 확인 페이지
     *
     * @return view
     */
//    @GetMapping("/receipt")
//    public String showOrderReceipt(Model model) {
//        int curCollect = orderService.getCurrentCollection(product);
//        model.addAttribute("curCollect", curCollect);
//        return "order/order_receipt";
//    }


    /**
     * 내가 투자한 금액 가져와서 전체 투자금액 갱신
     * post -> int
     * */
//    @GetMapping("/receipt")
//    public String showOrderReceipt(Model model) {
//        ProductDto productDto = productService.selectById(orderService.decEncId(encId));
//        int curCollect = orderService.getCurrentCollection(productDto);
//        model.addAttribute("curCollect", curCollect);
//        return "order/order_receipt";
//    }

    /**
     * 마이페이지에서 주문 상세보기
     * */

    /**
     * 주문 취소 -> 삭제
     */
//    @GetMapping("/delete/{encId}")
//    public String delete(@PathVariable String encId, Principal principal) {
//        Optional<Users> ou = userService.findByEmail(principal.getName());
//        UUID orderId = orderService.decEncId(encId);
//        if(ou.isPresent()){
//            Users user = ou.get();
//            orderService.delete(orderId, user);
//            return "redirect:/product/list";
//        }
//        //로그인한 유저의 정보가 없거나 삭제하려는 유저가 주문한 유저와 다를 경우
//        return "redirect:/order/list";
//
//    }

}

