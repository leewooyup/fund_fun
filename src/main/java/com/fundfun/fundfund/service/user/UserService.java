package com.fundfun.fundfund.service.user;

import com.fundfun.fundfund.domain.user.UserDTO;
import com.fundfun.fundfund.domain.user.Users;
import com.fundfun.fundfund.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final ModelMapper modelMapper;
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

    public UUID deleteById(UUID uuid) {
        userRepository.deleteById(uuid);
        return uuid;
    }

    public Users update(UUID uuid, UserDTO to) {
        return userRepository.findById(uuid).map(
                (x) -> userRepository.save(
                        modelMapper.map(to.builder().id(uuid).build(), Users.class)))
                .orElseThrow(NoSuchElementException::new);
    }



}
