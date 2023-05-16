package com.fundfun.fundfund.service.user;

import com.fundfun.fundfund.domain.user.Users;
import com.fundfun.fundfund.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<Users> findAll() {
        return userRepository.findAll();
    }

    public Optional<Users> findById(UUID id) {
        return userRepository.findById(id);
    }

    public Optional<Users> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Users register(Users users) {
        return userRepository.save(users);
    }

}
