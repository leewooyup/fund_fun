package com.fundfun.fundfund.service.alarm;

import com.fundfun.fundfund.domain.alarm.AlarmDTO;
import com.fundfun.fundfund.repository.user.AlarmRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class AlarmServiceImpl implements AlarmService {

    private final AlarmRepository alarmRepository;
    private final ModelMapper modelMapper;


    @Override
    public List<AlarmDTO> findAll() {
        return alarmRepository.findAll().stream().map(alarm -> modelMapper.map(alarm, AlarmDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<AlarmDTO> findByUserId(UUID id) {
        return alarmRepository.findByUserId(id).stream().map(alarm -> modelMapper.map(alarm, AlarmDTO.class)).collect(Collectors.toList());
    }


    @Override
    public AlarmDTO findById(Long id) {
        return alarmRepository.findById(id).map(x -> modelMapper.map(x, AlarmDTO.class)).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public AlarmDTO deleteById() {
        return null;
    }

    @Override
    public void updateAlarm() {

    }
}
