package com.fundfun.fundfund.service.user;

import com.fundfun.fundfund.domain.user.Gender;
import com.fundfun.fundfund.domain.user.Role;
import com.fundfun.fundfund.domain.user.UserDTO;
import com.fundfun.fundfund.domain.user.Users;
import org.junit.jupiter.api.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import static com.fundfun.fundfund.domain.user.Role.FUND_MANAGER;
import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    UserService userService;

    //0522 내가 수정한 코드

    @Rollback(value = false)
    @Test
    public void 테스트생성1(){

  /*      userService.register(Users.builder()
                .name("bana")
                .gender(Gender.valueOf("MALE"))
                .password("1234")
                .email("kb@kb.com")
                .phone("010-2323-1313")
                .money(12L)
                .count(23L)
                .total_investment(34L)
                .benefit(45L)
                .build());
*/
        userService.register(Users.builder()
                .email("angel@kb.com")
                .password("1234")
                .name("angel")
                        .role(FUND_MANAGER)
                .build());
    }

   /* @BeforeEach
    void setup() {
        userService.register(Users.builder()
                .role(Role.FUND_MANAGER)
                .email("test1")
                .password("1234")
                .name("kim")
                .build());
        userService.register(Users.builder()
                .role(Role.COMMON)
                .email("test2")
                .password("1234")
                .name("lee")
                .build());
        userService.register(Users.builder()
                .role(Role.COMMON)
                .email("test3")
                .password("1234")
                .name("park")
                .build());
    }

    @Test
    @DisplayName("유저 전체 조회 테스트")
    @Transactional
    void findAll() {
        //given
        //when
        List<Users> users = userService.findAll();
        //then
        assertThat(users.size() == 3);
    }

    @Test
    @DisplayName("유저 단건 조회 테스트")
    @Transactional
    void findById() {
        //given
        List<Users> users = userService.findAll();
        UUID id = users.get(0).getId();
        //when
        Optional<Users> user = userService.findById(id);
        //then
        assertThat(users.get(0).equals(user));
    }

    @Test
    @DisplayName("이메일 기반 유저 단건 조회 테스트")
    @Transactional
    void findByEmail() {
        //given
        Users user = userService.register(Users.builder()
                .role(Role.COMMON)
                .email("test4")
                .password("1234")
                .name("park")
                .build());
        //when
        Users found = userService.findByEmail("test4").get();
        //then
//        assertThat(user.getId())

    }

    @Test
    @DisplayName("유저 가입 테스트")
    @Transactional
    void register() {
        //given
        Users user = userService.register(Users.builder()
                .role(Role.COMMON)
                .email("test4")
                .password("1234")
                .name("park")
                .build());
        //when
        Users found = userService.findByEmail("test4").get();
        //then
        assertThat(user.getId()).isEqualTo(found.getId());
    }

    @Test
    @DisplayName("ID 기반 유저 삭제 테스트")
    @Transactional
    void deleteById() {
        Users user = userService.register(Users.builder()
                .role(Role.COMMON)
                .email("test5")
                .password("1234")
                .name("park")
                .build());
        //when
        UUID id = userService.findByEmail("test5").get().getId();
        userService.deleteById(id);
        //then
        assertThrows(Exception.class, () -> {
        System.out.println(userService.findById(id).get());
        });
    }

    @Test
    @DisplayName("유저 수정 테스트")
    void update() {
        //given
        List<Users> users = userService.findAll();
        //when
        Users user = users.get(0);
        UUID id = user.getId();
        UserDTO to = new ModelMapper().map(Users.builder().email("testEmail").build(), UserDTO.class);
        userService.update(user.getId(), to);
        //then
        assertThat(userService.findById(id).get().getEmail()).isEqualTo(to.getEmail());

    }*/
}