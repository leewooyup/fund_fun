package com.fundfun.fundfund.service.user;

import com.fundfun.fundfund.domain.user.Gender;
import com.fundfun.fundfund.domain.user.Role;
import com.fundfun.fundfund.domain.user.Users;
import com.fundfun.fundfund.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.fundfun.fundfund.domain.user.Role.FUND_MANAGER;

@Service
@RequiredArgsConstructor
public class UserServiceImpl {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public Users createUser() { //테스트용 code
        Users user = Users.builder()
                .name("bana")
                .gender(Gender.valueOf("MALE"))
                .password(passwordEncoder.encode("1234"))
                .email("kb@kb.com")
                .role(FUND_MANAGER)
                .phone("010-2323-1313")
                .money(12L)
                .count(23L)
                .total_investment(34L)
                .benefit(45L)
                .build();
        System.out.println("gd" + userRepository.findById(user.getId()).get().getRole());
        userRepository.save(user);
        return user;


    }


    public Users SelectById(UUID userId){
        return userRepository.findById(userId).orElse(null);
    }
}


