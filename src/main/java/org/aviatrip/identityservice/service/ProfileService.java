package org.aviatrip.identityservice.service;

import lombok.RequiredArgsConstructor;
import org.aviatrip.identityservice.dto.response.UserInfo;
import org.aviatrip.identityservice.dto.response.error.ResourceNotFoundResponse;
import org.aviatrip.identityservice.dto.response.error.ValueEqualsToOldValueResponse;
import org.aviatrip.identityservice.dto.response.error.ValueNotUniqueResponse;
import org.aviatrip.identityservice.entity.User;
import org.aviatrip.identityservice.exception.BadRequestException;
import org.aviatrip.identityservice.exception.InternalServerErrorException;
import org.aviatrip.identityservice.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final UserRepository userRepository;

    public UserInfo retrieveUserInfo(UUID userId) {
        return userRepository.findUserInfoById(userId)
                .orElseThrow(() -> new BadRequestException(new ResourceNotFoundResponse("user with " + userId + " id")));
    }

    public void updateName(String name, UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(InternalServerErrorException::new);

        if(user.getName().equals(name))
            throw new BadRequestException(new ValueEqualsToOldValueResponse("name"));

        user.setName(name);
        userRepository.save(user);
    }

    public void updateSurname(String surname, UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(InternalServerErrorException::new);

        if(user.getSurname().equals(surname))
            throw new BadRequestException(new ValueEqualsToOldValueResponse("surname"));

        user.setSurname(surname);
        userRepository.save(user);
    }

    public void updateEmail(String email, UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(InternalServerErrorException::new);

        if(user.getEmail().equals(email))
            throw new BadRequestException(new ValueEqualsToOldValueResponse("email"));

        assertEmailUniqueness(email);

        user.setEmail(email);
        userRepository.save(user);
    }

    public void assertEmailUniqueness(String email) {
        if(userRepository.existsByEmail(email))
            throw new BadRequestException(new ValueNotUniqueResponse("email"));
    }
}
