package com.fundfun.fundfund.controller.order;

import com.fundfun.fundfund.domain.user.Users;
import com.fundfun.fundfund.dto.order.InvestDto;
import com.fundfun.fundfund.dto.product.ProductDto;
import com.fundfun.fundfund.service.order.OrderService;
import com.fundfun.fundfund.exception.InSufficientMoneyException;
import com.fundfun.fundfund.service.order.OrderServiceImpl;
import com.fundfun.fundfund.service.product.ProductService;
import com.fundfun.fundfund.service.product.ProductServiceImpl;
import com.fundfun.fundfund.service.user.UserService;
import com.fundfun.fundfund.util.Util;
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

    private final OrderService orderService;
    private final ProductService productService;
    private final UserService userService;

    /**
     * 상품 Detail 페이지 + 투자 금액 입력 폼 페이지
     *
     * @param investDto
     * @return view
     */
    @GetMapping("/form/{encId}")
    public String showOrderForm(InvestDto investDto, Model model, @PathVariable String encId) {
        UUID uuid = orderService.decEncId(encId);//productId
        // 복호화된 uuid로 해당 product 가져오기
        ProductDto productDto = productService.selectById(uuid);
        model.addAttribute("product", productDto);
        model.addAttribute("encId", encId);

        int deadline = productService.crowdDeadline(productDto);
        model.addAttribute("deadline", deadline);

        return "order/order_form";
    }

    /**
     * 투자하기
     * user가 입력한 투자금액(cost) 투자 영수증으로 넘기기
     * @param investDto, bindingResult
     * @return view
     */
    @PostMapping("/send/{encId}")
    public String orderFormSend(@Valid InvestDto investDto, BindingResult bindingResult, @PathVariable String encId, Model model) {
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
     * @param encId
     * @param cost
     * @return poduct_list view
     */
    @PostMapping("/update/{encId}")
    public String update(@PathVariable String encId, Long cost, Principal principal) {
        Users user= userService.findByEmail(principal.getName()).orElse(null);
        ProductDto productDto = productService.selectById(orderService.decEncId(encId));
        try {
            int result = productService.updateCost(cost, productDto, user); //투자정보 갱신 + 주문서 만들기
            if (result == 0) {
                throw new RuntimeException("투자에 실패하셨습니다. 다시 시도해주세요.");
            }
        } catch (InSufficientMoneyException e) {
            String errMsg = Util.url.encode(e.getMessage());
            return String.format("redirect:/order/form/%s?errMsg=%s", encId, errMsg);
        } catch (RuntimeException e) {
            String errMsg = Util.url.encode(e.getMessage());
            return String.format("redirect:/order/form/%s?errMsg=%s", encId, errMsg);
        }
        String msg = Util.url.encode("성공적으로 투자되었습니다.");
        return String.format("redirect:/product/list?msg=%s", msg);
}

    /**
     * 마이페이지에서 주문 상세보기
     * */


    /**
     * 마이페이지에서 주문 취소 -> 삭제
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

