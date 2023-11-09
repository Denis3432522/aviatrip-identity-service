package org.aviatrip.identityservice.service;

import lombok.RequiredArgsConstructor;
import org.aviatrip.identityservice.dto.request.UserRequest;
import org.aviatrip.identityservice.dto.request.auth.AuthRequest;
import org.aviatrip.identityservice.dto.response.error.BadCredentialResponse;
import org.aviatrip.identityservice.dto.response.error.ErrorResponse;
import org.aviatrip.identityservice.dto.response.error.ValueEqualsToOldValueResponse;
import org.aviatrip.identityservice.dto.response.error.ValueNotUniqueResponse;
import org.aviatrip.identityservice.entity.User;
import org.aviatrip.identityservice.enumeration.Role;
import org.aviatrip.identityservice.exception.BadRequestException;
import org.aviatrip.identityservice.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationProvider authenticationProvider;

    public void createUser(UserRequest model) {
        assertEmailUnique(model.getEmail());

        User user = User.builder()
                .name(model.getName())
                .surname(model.getSurname())
                .email(model.getEmail())
                .role(model.getRole())
                .password(passwordEncoder.encode(model.getPassword()))
                .build();

        userRepository.save(user);
    }

    public Authentication authenticate(AuthRequest request) {
        try {
            return authenticationProvider
                    .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        } catch (AuthenticationException ex) {
            throw  new BadRequestException(new BadCredentialResponse());
        }
    }

    public String generateToken(Role role, String userId) {
        return jwtService.generateToken(role.getRoleName(), userId);
    }

    public void validate(String token) {
        try {
            jwtService.validateToken(token);
        } catch (Exception ex) {
            throw new BadRequestException(ErrorResponse.builder().errorMessage("invalid access token").build());
        }
    }

    public void assertEmailUnique(String email) {
        if(userRepository.existsByEmail(email))
            throw new BadRequestException(new ValueNotUniqueResponse("email"));
    }

    public void updatePassword(String rawPassword, UUID userId) {
        User user = userRepository.findById(userId).orElseThrow();

        if(passwordEncoder.matches(rawPassword, user.getPassword()))
            throw new BadRequestException(new ValueEqualsToOldValueResponse("password"));

        user.setPassword(passwordEncoder.encode(rawPassword));
        userRepository.save(user);
    }

    public void deleteUser(UUID userId) {
        userRepository.deleteById(userId);
    }
}
