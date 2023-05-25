package com.fundfun.fundfund.service.alarm;

import com.fundfun.fundfund.domain.alarm.AlarmDTO;
import com.fundfun.fundfund.domain.user.UserDTO;
import com.fundfun.fundfund.domain.user.Users;
import com.fundfun.fundfund.service.user.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.ui.ModelMap;

import javax.transaction.Transactional;


@SpringBootTest
class AlarmServiceImplTest {

    @Autowired
    private AlarmService alarmService;
    @Autowired
    private UserService userService;
    @Autowired
    private ModelMapper modelMapper;

    @Test
    void findAll() {

    }

    @Test
    void findByUserId() {
    }

    @Test
    void findById() {
    }

    @Test
    void deleteById() {
    }

    @Test
    void readAlarm() {
    }

    @Test
    void readAll() {
    }

    @Test
    void findAllUnread() {
    }

    @Test
    void testDeleteById() {
    }

    @Test
    @DisplayName("알람 생성 테스트")
    @Rollback(value = false)
    @Transactional
    public void createAlarmTest() throws Exception {
        //given
        UserDTO user = userService.findByEmail("user");
        System.out.println("user = " + user);
        //when
        alarmService.createAlarm(modelMapper.map(user, Users.class), "알람 33");
        alarmService.createAlarm(modelMapper.map(user, Users.class), "알람 34");
        alarmService.createAlarm(modelMapper.map(user, Users.class), "알람 35");
        alarmService.createAlarm(modelMapper.map(user, Users.class), "알람 36");
        alarmService.createAlarm(modelMapper.map(user, Users.class), "알람 37");
        System.out.println(userService.findByEmail("user").getAlarms().toString());


//        System.out.println("alarmDTO = " + alarmDTO);
        //then
//        System.out.println("alarmDTO = " + alarmDTO.getId());
//        Assertions.assertThat(alarmService.findById().getContent()).isEqualTo(alarmDTO.getContent());

    }
}