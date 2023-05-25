package com.fundfun.fundfund.service.alarm;

import com.fundfun.fundfund.domain.alarm.AlarmDTO;
import com.fundfun.fundfund.domain.user.Users;

import java.util.List;
import java.util.UUID;

public interface AlarmService {

    AlarmDTO createAlarm(Users user, String content);
    List<AlarmDTO> findAll();

    List<AlarmDTO> findByUserId(UUID id);

    AlarmDTO findById(Long id);

    AlarmDTO deleteById();

    void readAlarm(Long id);

    List<AlarmDTO> findAllUnread(UUID id);
    void readAll(UUID id);

    void deleteById(Long id);
}
