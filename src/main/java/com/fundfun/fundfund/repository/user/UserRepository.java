package com.fundfun.fundfund.repository.user;

import com.fundfun.fundfund.domain.user.Role;
import com.fundfun.fundfund.domain.user.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<Users, UUID> {

    Optional<Users> findByEmail(String email);

    List<Users> findByRole(Role role);
    static final String UPDATE_MEMBER_LAST_LOGIN = "UPDATE users SET LAST_LOGIN_TIME = :lastLoginTime WHERE EMAIL = :email";

    @Transactional
    @Modifying
    @Query(value = UPDATE_MEMBER_LAST_LOGIN, nativeQuery = true)
    public int updateMemberLastLogin(@Param("email") String email, @Param("lastLoginTime") LocalDateTime lastLoginTime);
}
