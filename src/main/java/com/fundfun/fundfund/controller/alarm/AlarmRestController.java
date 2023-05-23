package com.fundfun.fundfund.controller.alarm;

import com.fundfun.fundfund.domain.alarm.AlarmDTO;
import com.fundfun.fundfund.domain.user.Users;
import com.fundfun.fundfund.repository.user.UserRepository;
import com.fundfun.fundfund.service.alarm.AlarmService;
import com.fundfun.fundfund.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ff/api/v1/alarm")
@Slf4j
public class AlarmRestController {
    private final UserRepository userRepository;
    private final AlarmService alarmService;
//    private final ObjectMapper objectMapper;

    /**
     * @author - yeoooo
     * 읽지 않은 알림만 로드
     */
    @GetMapping("/")
    public ApiResponse<List<AlarmDTO>> getUnreads(@AuthenticationPrincipal Users user) {
        List<AlarmDTO> alarms = alarmService.findAllUnread(user.getId());
        log.info("[AlarmRestController] - user {} requested Unread Alarm -> {}", user.getEmail(), alarms.toString());
        return ApiResponse.success(alarms);
    }

    /**
     * @author yeoooo
     * 한 유저에 관한 알림을 읽기 여부에 관계 없이 로드.
     */
    @GetMapping("/all")
    public ApiResponse<List<AlarmDTO>> getAll(@AuthenticationPrincipal Users user) {
        return ApiResponse.success(alarmService.findByUserId(user.getId()));
    }

    /**
     * @author yeoooo
     * 하나의 알림을 읽음 상태로 변경
     */
    @PostMapping("/read/{id}")
    public ApiResponse<String> readOne(@PathVariable("id") Long id) {
        alarmService.readAlarm(id);
        return ApiResponse.success("success");
    }

    /**
     * @author yeoooo
     * 모든 알림을 읽음 상태로 변경
     */
    @PostMapping("/read/all")
    public ApiResponse<String> readAll(@AuthenticationPrincipal Users user) {
        alarmService.readAll(user.getId());
        log.info("[AlarmRestController] - read All () executed");
        return ApiResponse.success("good test");
    }

    @PostMapping("/delete/{id}")
    public ApiResponse<String> deleteById(@PathVariable("id") Long id) {
        alarmService.deleteById(id);
        return ApiResponse.success("success");
    }
}
