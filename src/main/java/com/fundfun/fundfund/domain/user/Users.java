package com.fundfun.fundfund.domain.user;

import com.fundfun.fundfund.domain.alarm.Alarm;
import com.fundfun.fundfund.domain.order.Orders;
import com.fundfun.fundfund.domain.payment.PayMean;
import com.fundfun.fundfund.domain.payment.Payment;
import com.fundfun.fundfund.domain.portfolio.Portfolio;
import com.fundfun.fundfund.domain.product.Product;
import com.fundfun.fundfund.domain.vote.Vote;

import com.fundfun.fundfund.util.BaseTimeEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
//@ToString
@NoArgsConstructor
@Builder
@Table(name = "users")
@AllArgsConstructor
public class Users extends BaseTimeEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private UUID id;

    @OneToMany(mappedBy = "user")
    private List<Portfolio> on_vote_portfolio = new ArrayList<>();

    @OneToMany(mappedBy = "fundManager")
    private final List<Product> managing_product = new ArrayList<>();

    @OneToMany
    private List<Alarm> alarms = new ArrayList<>();

    @OneToMany
    private List<Payment> payments = new ArrayList<>();

    @OneToMany
    private List<PayMean> means = new ArrayList<>();

    private String password;

    private String name;
    private String email;
    private Role role;
    private String phone;
    private Gender gender;
    private LocalDateTime reg_date;
    private Long money;
    private Long count;
    private Long total_investment;
    private Long benefit;
    private LocalDateTime lastLoginTime;

    public void addAlarm(Alarm alarm) {
        alarms.add(alarm);
    }

    public void addPayMean(PayMean payMean) {
        means.add(payMean);
    }

    public void addPayment(Payment payment) {
        payments.add(payment);
    }

    public void addPortfolio(Portfolio portfolio) {
        on_vote_portfolio.add(portfolio);
    }

    public void addProduct(Product product) {
        managing_product.add(product);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collectors = new ArrayList<>();
        collectors.add(() -> {
            return "ROLE_USER";
        });
        return collectors;
    }
    public Users updateOAuth(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() { return true;  }

    public void setMoney(Long money){ //by lee
        this.money = money;
    }
}
