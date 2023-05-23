package com.fundfun.fundfund.service.user;

import com.fundfun.fundfund.domain.user.Gender;
import com.fundfun.fundfund.domain.user.UserDTO;
import com.fundfun.fundfund.domain.user.Users;
import com.fundfun.fundfund.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    public Users createUser(){ //테스트용code
        Users user = Users.builder()
                .name("bana")
                .gender(Gender.valueOf("MALE"))
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


