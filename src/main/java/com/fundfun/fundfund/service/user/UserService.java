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


public interface UserService {
    List<UserDTO> findAll();
    UserDTO findById(UUID id);
    UserDTO findByEmail(String email);
    UserDTO register(Users users);
    UUID deleteById(UUID uuid);
    Users update(UUID uuid, UserDTO to);
    Long addMoney(UserDTO dto, Long amount);
    Long minusMoney(UserDTO dto, Long amount);
}