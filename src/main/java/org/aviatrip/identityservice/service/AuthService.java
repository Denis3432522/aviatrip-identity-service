package org.aviatrip.identityservice.service;

import lombok.RequiredArgsConstructor;
import org.aviatrip.identityservice.dto.request.UserRequest;
import org.aviatrip.identityservice.dto.request.auth.AuthRequest;
import org.aviatrip.identityservice.dto.response.error.*;
import org.aviatrip.identityservice.entity.User;
import org.aviatrip.identityservice.enumeration.Role;
import org.aviatrip.identityservice.exception.BadRequestException;
import org.aviatrip.identityservice.exception.FatalKafkaException;
import org.aviatrip.identityservice.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationProvider authenticationProvider;

    @Transactional
    public User createUser(UserRequest dto) {
        assertEmailUnique(dto.getEmail());

        User user = User.builder()
                .name(dto.getName())
                .surname(dto.getSurname())
                .email(dto.getEmail())
                .role(dto.getRole())
                .password(passwordEncoder.encode(dto.getPassword()))
                .build();

        return userRepository.save(user);
    }

    public Authentication authenticate(AuthRequest dto) {
        try {
            return authenticationProvider
                    .authenticate(new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword()));
        } catch (AuthenticationException ex) {
            throw new BadRequestException(new BadCredentialResponse());
        }
    }

    public String generateToken(Role role, String userId) {
        return jwtService.generateToken(role.toString(), userId);
    }

    public void validate(String token) {
        try {
            jwtService.validateToken(token);
        } catch (Exception ex) {
            throw new BadRequestException(ErrorResponse.builder()
                    .errorMessage("invalid access token")
                    .build()
            );
        }
    }

    @Transactional
    public void assertEmailUnique(String email) {
        if(userRepository.existsByEmail(email))
            throw new BadRequestException(new ValueNotUniqueResponse("email"));
    }

    @Transactional
    public void updatePassword(String rawPassword, UUID userId) {
        User user = userRepository.findById(userId).orElseThrow();

        if(passwordEncoder.matches(rawPassword, user.getPassword()))
            throw new BadRequestException(new ValueEqualsToOldValueResponse("password"));

        user.setPassword(passwordEncoder.encode(rawPassword));
        userRepository.save(user);
    }

    @Transactional
    public void deleteUser(UUID userId) {
        int rowCount = userRepository.deleteUserById(userId);
        if(rowCount == 0)
            throw new BadRequestException(new ResourceNotFoundResponse("User with ID " + userId));
    }

    @Transactional
    public void updateRole(UUID userId, Role role) {
        int modifiedRowsCount = userRepository.updateRoleById(role, userId);
        if(modifiedRowsCount == 0)
            throw new FatalKafkaException("User with ID " + userId + " not found");
    }
}
