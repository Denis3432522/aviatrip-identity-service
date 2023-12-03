package org.aviatrip.identityservice.repository;


import org.aviatrip.identityservice.dto.response.UserInfo;
import org.aviatrip.identityservice.entity.User;
import org.aviatrip.identityservice.enumeration.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<UserInfo> findUserInfoById(UUID id);

    Optional<User> findByEmail(String username);
    boolean existsByEmail(String email);

    @Modifying
    @Query("delete from User u where u.id = ?1")
    int deleteUserById(UUID id);

    @Modifying
    @Query("update User u set u.role = ?1 where u.id = ?2")
    int updateRoleById(Role role, UUID userId);
}
