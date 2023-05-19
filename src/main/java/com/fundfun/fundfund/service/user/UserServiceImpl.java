package com.fundfun.fundfund.service.user;

import com.fundfun.fundfund.domain.user.Users;
import com.fundfun.fundfund.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl {

    private final UserRepository userRepository;
    public Users createUser(){ //테스트용code
        Users user = Users.builder()
                .name("bana")
                .gender("남")
                .password("1234")
                .email("kb@kb.com")
                .phone("010-2323-1313")
                .money(12L)
                .count(23L)
                .total_investment(34L)
                .benefit(45L)
                .build();

        userRepository.save(user);
        return user;
    }

    public Users SelectById(UUID userId){
        return userRepository.findById(userId).orElse(null);
    }
}


