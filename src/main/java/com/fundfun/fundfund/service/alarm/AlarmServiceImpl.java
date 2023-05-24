package com.fundfun.fundfund.service.alarm;

import com.fundfun.fundfund.domain.alarm.Alarm;
import com.fundfun.fundfund.domain.alarm.AlarmDTO;
import com.fundfun.fundfund.domain.user.UserDTO;
import com.fundfun.fundfund.domain.user.Users;
import com.fundfun.fundfund.repository.user.AlarmRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AlarmServiceImpl implements AlarmService {

    private final AlarmRepository alarmRepository;
    private final ModelMapper modelMapper;


    @Override
    public AlarmDTO createAlarm(Users user, String content) {
        Alarm alarm = Alarm.createAlarm(user, content);

        alarmRepository.save(alarm);
        return modelMapper.map(alarm, AlarmDTO.class);
    }

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
    public void readAlarm(Long id) {
        Alarm found = alarmRepository.findById(id).orElseThrow(NoSuchElementException::new);
        alarmRepository.save(Alarm.builder()
                .content(found.getContent())
                .is_read(true)
                .user(found.getUser())
                .build());
    }

    @Override
    @Transactional
    public void readAll(UUID id) {
        List<Alarm> unread = alarmRepository.findAllUnread(id);
        for (Alarm alarm :
                unread) {
            alarm.read();
        }
    }

    @Override
    public List<AlarmDTO> findAllUnread(UUID id) {
        return alarmRepository.findAllUnread(id)
                .stream().map(
                        x -> modelMapper.map(x, AlarmDTO.class)
                ).collect(Collectors.toList());
    }

    public void deleteById(Long id) {
        alarmRepository.deleteById(id);
    }

}
