package org.aviatrip.identityservice.repository;


import org.aviatrip.identityservice.dto.response.error.UserInfo;
import org.aviatrip.identityservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<UserInfo> findUserInfoById(UUID id);

    Optional<User> findByEmail(String username);
    boolean existsByEmail(String email);
}
