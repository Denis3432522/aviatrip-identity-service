package org.aviatrip.identityservice.repository;

import org.aviatrip.identityservice.dto.response.error.UserInfo;
import org.aviatrip.identityservice.entity.User;
import org.aviatrip.identityservice.enumeration.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    void findUserInfoByIdTest() {
        User testUser = User.builder()
                .name("fds")
                .surname("fdsfsd")
                .email("fdsf@mail.com")
                .password("fsdpj32jf32pofj32op")
                .role(Role.ROLE_CUSTOMER)
                .build();

        User savedUser = userRepository.save(testUser);

        assertTrue(userRepository.findUserInfoById(savedUser.getId()).isPresent());

        UserInfo userInfo = userRepository.findUserInfoById(savedUser.getId()).get();

        UserInfo testUserInfo = new UserInfo(savedUser.getId(),
                testUser.getName(),
                testUser.getSurname(),
                testUser.getEmail(),
                testUser.getBalance(),
                testUser.getRole(),
                savedUser.getCreatedAt()
        );

        assertThat(userInfo).isEqualTo(testUserInfo);
    }
}
