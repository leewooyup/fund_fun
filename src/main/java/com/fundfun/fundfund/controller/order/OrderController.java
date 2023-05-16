//package com.fundfun.fundfund.controller.order;
//
//import com.fundfun.fundfund.domain.order.Orders;
//import com.fundfun.fundfund.domain.product.Product;
//import com.fundfun.fundfund.service.order.OrderService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import java.util.List;
//import java.util.UUID;
//
//@Controller
//@RequestMapping("/order")
//public class OrderController {
//
//    @Autowired
//    private OrderService orderService;
//
//    /**
//     * 전체 검색
//     */
//    @RequestMapping("/list")
//    public void list(Model model)
//
//
//
//
//    /**
//     * 등록
//     */
//    @GetMapping("/insert")
//    public String insert(Orders order, Product product, Model model) {
//
//
//
//    }
//
//    /**
//     * 상세보기
//     * */
//
//    /**
//     * 주문 취소 -> 삭제
//     */
//    @RequestMapping("/delete")
//    public String delete(UUID id, String password) {
//
//
//        return "redirect:/order/list";
//
//    }
//}