package com.fundfun.fundfund.repository.user;

import com.fundfun.fundfund.domain.user.Role;
import com.fundfun.fundfund.domain.user.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<Users, UUID> {

    Optional<Users> findByEmail(String email);

    List<Users> findByRole(Role role);
}
