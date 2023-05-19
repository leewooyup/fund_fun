package com.fundfun.fundfund.controller.user;

import com.fundfun.fundfund.domain.user.*;
import com.fundfun.fundfund.repository.user.UserRepository;
import com.fundfun.fundfund.service.user.UserService;
import com.fundfun.fundfund.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("ff/api/v1/")
@RequiredArgsConstructor
@Slf4j
public class UserRestController {
    private final UserRepository userRepository;
    private final UserService userService;
    private final BCryptPasswordEncoder encoder;
    private final ModelMapper modelMapper;

    @PostMapping("register")
    public ApiResponse<String> register(@RequestBody Map<String, Object> resp, @RequestParam("role") String role) {

        try {
            Users user = userService.register(
                    userService.register(Users.builder()
                            .count(0L)
                            .email((String) resp.get("email"))
                            .gender(Gender.valueOf((String) resp.get("gender")))
                            .money(0L)
                            .benefit(0L)
                            .name((String) resp.get("name"))
                            .phone((String) resp.get("phone"))
                            .password(encoder.encode((String) resp.get("pw")))
                            .role(Role.valueOf(role))
                            .total_investment(0L)
                            .build())
            );
            log.info("[UserController] ]User Role {} has been registered.", user.getRole(), user.toString());
            return ApiResponse.success("success");

        } catch (RuntimeException e) {
            // 2023-05-17_yeoooo : RuntimeException은 적절한 예외로 대체되어야 함.
            e.printStackTrace();
            return ApiResponse.fail(500, e.getMessage());
        }
    }

    @GetMapping("/users")
    public ApiResponse<List<UserDTO>> findAll() {
        return ApiResponse.success(userRepository.findAll()
                .stream().map((x) -> modelMapper.map(x, UserDTO.class)).collect(Collectors.toList()));
    }

    @GetMapping("/user/{email}")
    public ApiResponse<UserDTO> findByEmail(@PathVariable String email) {
        return ApiResponse.success(userRepository.findByEmail(email).map(
                        (x) -> modelMapper.map(x, UserDTO.class))
                .orElseThrow(NoSuchElementException::new));
    }

    @PostMapping("/user/{email}")
    public ApiResponse<String> deleteByEmail(@PathVariable String email, @RequestParam("method") String method) {
        userRepository.delete(
                userRepository.findByEmail(email).map(users -> modelMapper.map(users, Users.class))
                        .orElseThrow(NoSuchElementException::new));
        return ApiResponse.success("success");
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ApiResponse<String> handleExceptions(Exception e) {
        String err = "";
        if (e.getClass().equals(NoSuchElementException.class)){
            err = e.getMessage();
            return ApiResponse.fail(500,e.getMessage());
        }
        else{
            return ApiResponse.fail(500, new RuntimeException().getMessage());
        }
    }

}
