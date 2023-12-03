package org.aviatrip.identityservice.repository;

import jakarta.transaction.Transactional;
import org.aviatrip.identityservice.dto.response.UserInfo;
import org.aviatrip.identityservice.entity.User;
import org.aviatrip.identityservice.enumeration.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    void testFindUserInfoByIdWhenUserExistsThenReturnUserInfo() {
        User testUser = User.builder()
                .name("John")
                .surname("Doe")
                .email("john.doe@mail.com")
                .password("password")
                .role(Role.ROLE_CUSTOMER)
                .build();

        User savedUser = userRepository.save(testUser);
        Optional<UserInfo> retrievedUserInfo = userRepository.findUserInfoById(savedUser.getId());

        assertTrue(retrievedUserInfo.isPresent());
        assertEquals(savedUser.getId(), retrievedUserInfo.get().getId());
    }

    @Test
    void testFindByEmailWhenUserExistsThenReturnUser() {
        User testUser = User.builder()
                .name("John")
                .surname("Doe")
                .email("john.doe@mail.com")
                .password("password")
                .role(Role.ROLE_CUSTOMER)
                .build();

        User savedUser = userRepository.save(testUser);
        Optional<User> retrievedUser = userRepository.findByEmail(savedUser.getEmail());

        assertTrue(retrievedUser.isPresent());
        assertEquals(savedUser.getEmail(), retrievedUser.get().getEmail());
    }

    @Test
    void testExistsByEmailWhenUserExistsThenReturnTrue() {
        User testUser = User.builder()
                .name("John")
                .surname("Doe")
                .email("john.doe@mail.com")
                .password("password")
                .role(Role.ROLE_CUSTOMER)
                .build();

        userRepository.save(testUser);
        boolean exists = userRepository.existsByEmail(testUser.getEmail());

        assertTrue(exists);
    }

    @Test
    @Transactional
    void testDeleteUserByIdWhenUserExistsThenReturnOne() {
        User testUser = User.builder()
                .name("John")
                .surname("Doe")
                .email("john.doe@mail.com")
                .password("password")
                .role(Role.ROLE_CUSTOMER)
                .build();

        User savedUser = userRepository.save(testUser);
        int deletedRows = userRepository.deleteUserById(savedUser.getId());

        assertEquals(1, deletedRows);
    }
}
