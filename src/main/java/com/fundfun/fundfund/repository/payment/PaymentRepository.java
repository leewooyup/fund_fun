package com.fundfun.fundfund.repository.payment;

import com.fundfun.fundfund.domain.payment.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, UUID> {

    String FIND_ALL_BY_USER_ID = "select p from Payment p where p.user_id = :id";

    @Query(value = FIND_ALL_BY_USER_ID)
    List<Payment> findAllByUserId(@Param("id") UUID id);
}
