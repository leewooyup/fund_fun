package com.fundfun.fundfund.domain.user;

import com.fundfun.fundfund.domain.alarm.AlarmDTO;
import com.fundfun.fundfund.domain.order.Orders;
import com.fundfun.fundfund.domain.payment.PaymentMeanDTO;
import com.fundfun.fundfund.domain.post.Post;
import com.fundfun.fundfund.dto.portfolio.PortfolioDto;
import com.fundfun.fundfund.dto.product.ProductDto;
import com.fundfun.fundfund.service.payment.PaymentDTO;
import lombok.*;

import java.time.LocalDateTime;

import java.util.List;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
public class UserDTO {
    private UUID id;
    private String name;
    private String email;
    private Role role;
    private String phone;
    private Gender gender;
    private LocalDateTime reg_date;
    private Long money;
    private String password;
    private Long count;
    private Long total_investment;
    private Long benefit;
    private String image;
    private List<PortfolioDto> on_vote_portfolio;
    private List<ProductDto> managing_product;
    private List<AlarmDTO> alarms;
    private List<PaymentDTO> payments;
    private List<PaymentMeanDTO> means;
    private List<Orders> orders;
    private List<Post> posts;


}
