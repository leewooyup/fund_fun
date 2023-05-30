package com.fundfun.fundfund.repository.user;

import com.fundfun.fundfund.domain.alarm.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {
    String FIND_ALL_UNREADEN = "SELECT a FROM Alarm a WHERE a.is_read = 0 and user_id = :id";
    List<Alarm> findByUserId(UUID id);

    @Query(value = FIND_ALL_UNREADEN)
    List<Alarm> findAllUnread(@Param("id") UUID id);

}
