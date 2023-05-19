package com.fundfun.fundfund.repository.user;

import com.fundfun.fundfund.domain.alarm.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {
    List<Alarm> findByUserId(UUID id);

}
