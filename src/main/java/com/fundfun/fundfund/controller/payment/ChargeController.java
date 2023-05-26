package com.fundfun.fundfund.controller.payment;

import com.fundfun.fundfund.domain.payment.ChargeForm;
import com.fundfun.fundfund.domain.payment.PayMean;
import com.fundfun.fundfund.domain.payment.PayMeanForm;
import com.fundfun.fundfund.domain.user.UserAdapter;
import com.fundfun.fundfund.domain.user.UserDTO;
import com.fundfun.fundfund.service.payment.PaymentService;
import com.fundfun.fundfund.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class ChargeController {
    private final UserService userService;
    private final PaymentService paymentService;

    /**
     * @@Author yeoooo
     * 충전 페이지에 유저 결제 수단과 충전 폼을 전달하는 메서드
     */
    @GetMapping("/charge")
    public String charge(@AuthenticationPrincipal UserAdapter adapter, Model model) {
        UserDTO user = userService.findById(adapter.getUser().getId());

        model.addAttribute("means", paymentService.findAllPayMeanByUserId(user.getId()));
        model.addAttribute("chargeForm", new ChargeForm());
        return null;
    }

    /**
     * @Author yeoooo
     * Form을 입력 받아 결제를 수행 하는 메서드
     */
    @PostMapping("/charge")
    public String chargeBalance(@AuthenticationPrincipal UserAdapter adapter, @ModelAttribute("chargeForm") ChargeForm form) {
        UserDTO user = userService.findById(adapter.getUser().getId());
        PayMean mean = form.getMean();
        /*
            2023-05-25_yeoooo :
            결제 API가 추가되면 좋음.
            현재 별도의 Validation 없이 잔액을 충전

         */
        Long amount = form.getAmount();
        userService.addMoney(user, amount);
        return "redirect:/charge";
    }

    @PostMapping("/charge/means?action=add")
    public String addMean(@AuthenticationPrincipal UserAdapter adapter,
                          @ModelAttribute("meanForm") PayMeanForm mean) {

        UserDTO user = userService.findById(adapter.getUser().getId());
        paymentService.addPayMean(mean.getMean(), mean.getMean_id(), mean.getCvc(), mean.getVendor(), adapter.getUser());

        return "redirect:/charge";
    }

    @PostMapping("/charge/means/{id}?action=delete")
    public String deleteMean(@AuthenticationPrincipal UserAdapter adapter,
                             @PathVariable("id") Long id) {
        paymentService.deletePayMeanById(id);
        return "redirect:/charge";
    }

}
