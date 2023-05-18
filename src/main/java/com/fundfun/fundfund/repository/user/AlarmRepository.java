package com.fundfun.fundfund.repository.user;

import com.fundfun.fundfund.domain.alarm.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {
//    findByUser
}
