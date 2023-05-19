package com.fundfun.fundfund.service.alarm;

import com.fundfun.fundfund.domain.alarm.Alarm;
import com.fundfun.fundfund.domain.alarm.AlarmDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AlarmService {
    List<AlarmDTO> findAll();

    List<AlarmDTO> findByUserId(UUID id);

    AlarmDTO findById(Long id);

    AlarmDTO deleteById();

    void updateAlarm();



}
