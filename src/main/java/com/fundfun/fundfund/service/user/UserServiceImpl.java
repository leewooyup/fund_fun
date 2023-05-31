package com.fundfun.fundfund.service.user;

import com.fundfun.fundfund.domain.user.Gender;
import com.fundfun.fundfund.domain.user.Role;
import com.fundfun.fundfund.domain.user.UserDTO;
import com.fundfun.fundfund.domain.user.Users;
import com.fundfun.fundfund.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    @Override
    public List<UserDTO> findAll() {
        return userRepository.findAll().stream().map(x -> modelMapper.map(x, UserDTO.class)).collect(Collectors.toList());
    }

    @Override
    public UserDTO findById(UUID id) {
        return userRepository.findById(id).map(x -> modelMapper.map(x, UserDTO.class)).orElseThrow(NoSuchElementException::new);

    }

    @Override
    public UserDTO findByEmail(String email) {
        return userRepository.findByEmail(email).map(x -> modelMapper.map(x, UserDTO.class)).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public Long addMoney(UserDTO dto, Long amount) {
        Users user = modelMapper.map(dto, Users.class);
        user.addMoney(amount);
        userRepository.save(user);

        return user.getMoney();
    }

    @Override
    public Long minusMoney(UserDTO dto, Long amount) {
        Users user = modelMapper.map(dto, Users.class);
            user.minusMoney(amount);
            return user.getMoney();
    }

    public UserDTO register(Users users) {
        return modelMapper.map(userRepository.save(users), UserDTO.class);
    }

    @Override
    public UUID deleteById(UUID uuid) {
        userRepository.deleteById(uuid);
        return null;
    }

    public Users update(UUID uuid, UserDTO to) {
        return userRepository.findById(uuid).map(
                        (x) -> userRepository.save(
                                modelMapper.map(to, Users.class)))
                .orElseThrow(NoSuchElementException::new);
    }


}