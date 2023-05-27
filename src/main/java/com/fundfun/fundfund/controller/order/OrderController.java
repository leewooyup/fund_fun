package com.fundfun.fundfund.controller.order;


import com.fundfun.fundfund.domain.order.Orders;
import com.fundfun.fundfund.domain.product.Items;
import com.fundfun.fundfund.domain.product.Product;
import com.fundfun.fundfund.domain.product.Weight;
import com.fundfun.fundfund.domain.user.UserAdapter;
import com.fundfun.fundfund.domain.user.UserDTO;
import com.fundfun.fundfund.domain.user.Users;
import com.fundfun.fundfund.dto.order.InvestDto;
import com.fundfun.fundfund.dto.product.ProductDto;
import com.fundfun.fundfund.service.order.OrderService;
import com.fundfun.fundfund.exception.InSufficientMoneyException;
import com.fundfun.fundfund.service.order.OrderServiceImpl;
import com.fundfun.fundfund.service.product.ProductService;
import com.fundfun.fundfund.service.product.ProductServiceImpl;
import com.fundfun.fundfund.service.user.UserService;
import com.fundfun.fundfund.util.UserMapper;
import com.fundfun.fundfund.util.Util;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
     * @param investDto
     * @return view
     */
    @GetMapping("/form/{encId}")
    public String showOrderForm(InvestDto investDto, Model model, @PathVariable String encId, @AuthenticationPrincipal UserAdapter adapter) {
        UUID uuid = orderService.decEncId(encId);//productId
        // 복호화된 uuid로 해당 product 가져오기
        ProductDto productDto = productService.selectById(uuid);
        model.addAttribute("product", productDto);
        model.addAttribute("encId", encId);
        productService.updateStatus(productDto);
        int deadline = productService.crowdDeadline(productDto);
        model.addAttribute("deadline", deadline);

        UserDTO userDTO = userService.findByEmail(adapter.getUser().getEmail());
        if(userDTO.getId() == productDto.getFundManager().getId()){
            model.addAttribute("user", userDTO);
        }

        List<Items> items = productService.selectItemsByProductTitle(productDto.getTitle());
        List<Weight> weights = productService.selectWeightsByProductTitle(productDto.getTitle());
        String[] itemNames = new String[items.size()];
        Integer[] itemWeights = new Integer[weights.size()];
        int idx = 0;
        for(Items it : items) {
            itemNames[idx++] = it.getItemsName();
        }
        idx = 0;
        for(Weight w : weights) {
            itemWeights[idx++] = w.getWeight();
        }

        model.addAttribute("items", itemNames);
        model.addAttribute("weights", itemWeights);

        List<String> combinedList = new ArrayList<>();
        for(int i = 0; i < itemNames.length; i++) {
            String combinedValue = "\'" + itemNames[i] + "\'" + " 상품: " + itemWeights[i] + " %";
            combinedList.add(combinedValue);
        }
        model.addAttribute("combinedList", combinedList);
        return "order/order_form";
    }

    /**
     * 투자하기
     * user가 입력한 투자금액(cost) 투자 영수증으로 넘기기
     * @param investDto
     * @return view
     */
    @PostMapping("/send/{encId}")
    public String orderFormSend(@Valid InvestDto investDto, BindingResult bindingResult, @PathVariable String encId, Model model, @AuthenticationPrincipal UserAdapter adapter) {
        if (bindingResult.hasErrors()) {
            return "order/order_form";
        }

        UUID productId = orderService.decEncId(encId);
        ProductDto productDto = productService.selectById(productId); //현재 product의 정보 가져오기

        UserDTO userDTO = userService.findByEmail(adapter.getUser().getEmail());

        if (productDto == null || investDto == null) {
            throw new RuntimeException("투자에 실패하셨습니다.");
        }

        model.addAttribute("product", productDto);
        model.addAttribute("invest", investDto);
        model.addAttribute("encId", encId);
        model.addAttribute("user",userDTO );

        return "order/order_receipt";
    }

    /**
     * 주문 생성 + 상품 모금액 업데이트
     * @param encId
     * @param cost
     * @return view
     */
    @PostMapping("/update/{encId}")
    public String update(@AuthenticationPrincipal UserAdapter adapter, @PathVariable String encId, Long cost, HttpServletRequest req) {
        UserDTO userDTO = userService.findByEmail(adapter.getUser().getEmail());
        ProductDto productDto = productService.selectById(orderService.decEncId(encId));

        try {
            //User Point update
            if (userDTO.getMoney() < cost) {
                throw new InSufficientMoneyException("충전이 필요합니다.");
            }

            //주문서 생성 + 유저 충전금 업데이트
            Orders order = orderService.createOrder(cost, productDto.getId(), userDTO.getId());
            if (order == null) {
                throw new RuntimeException("투자에 실패하셨습니다. 다시 시도해주세요.");
            }

            //Product update
            int result = productService.updateCost(cost, productDto, userDTO); //투자정보 갱신
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

        //업데이트된 유저를 다시 불러옴(유저 충전금 업데이트)
        UserDTO updateUserDTO = userService.findByEmail(adapter.getUser().getEmail());


//        model.addAttribute("user", updateUserDTO);
//        model.addAttribute("product", productDto);
//        model.addAttribute("cost", cost);
//        return "order/order_confirm";

        //return String.format("redirect:/product/list?msg=%s", msg);
        req.setAttribute("product", productDto);
        req.setAttribute("user", updateUserDTO);
        req.setAttribute("cost", cost);
        return String.format("forward:/order/confirm?msg=%s", msg);

    }

    @PostMapping("/confirm")
    public String showOrderConfirm(HttpServletRequest req, Model model, Long cost) {
        ProductDto product = (ProductDto) req.getAttribute("product");
        UserDTO user = (UserDTO) req.getAttribute("user");
        model.addAttribute("product", product);
        model.addAttribute("user", user);
        model.addAttribute("cost", cost);
        return "order/order_confirm";
    }

    /**
     * 마이페이지에서 주문 상세보기
     * */


    /**
     * 마이페이지에서 주문 취소 -> 삭제
     */
//    @GetMapping("/delete/{encId}")
//    public String delete(@PathVariable String encId, Principal principal) {
//        UserDTO userDTO = userService.findByEmail(principal.getName());
//        UUID orderId = orderService.decEncId(encId);
//        if (orderService.selectById(orderId).getUser().equals(userDTO)) {
//            orderService.delete(orderId, userDTO);
//            return "redirect:/product/list";
//        }
//        //로그인한 유저의 정보가 없거나 삭제하려는 유저가 주문한 유저와 다를 경우
//        return "redirect:/order/list";
//    }


//    @PostMapping("/confirm/{encId}")
//    public String confirm(@PathVariable String encId){
//        System.out.println("encId = " + encId);
//
//        return "order/order_confirm";
//    }

}
