package org.aviatrip.identityservice.service;

import org.aviatrip.identityservice.dto.request.auth.AuthRequest;
import org.aviatrip.identityservice.enumeration.Role;
import org.aviatrip.identityservice.exception.BadRequestException;
import org.aviatrip.identityservice.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationProvider authenticationProvider;

    @InjectMocks
    private AuthService authService;
    private AuthRequest authRequest;
    private String token;
    private UUID userId;

    @BeforeEach
    public void setUp() {
        authRequest = new AuthRequest();
        token = "token";
        userId = UUID.randomUUID();
    }

    @Test
    public void testAuthenticateWhenValidCredentialsThenAuthenticated() {
        when(authenticationProvider.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(null);

        authService.authenticate(authRequest);

        verify(authenticationProvider, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    public void testGenerateTokenWhenValidRoleAndUserIdThenTokenGenerated() {
        when(jwtService.generateToken(anyString(), anyString())).thenReturn(token);

        String result = authService.generateToken(Role.ROLE_CUSTOMER, userId.toString());

        assertNotNull(result);
        assertFalse(result.isEmpty());
        verify(jwtService, times(1)).generateToken(anyString(), anyString());
    }

    @Test
    public void testValidateWhenValidTokenThenTokenValidated() {
        doNothing().when(jwtService).validateToken(anyString());

        authService.validate(token);

        verify(jwtService, times(1)).validateToken(anyString());
    }

    @Test
    public void testAssertEmailUniqueWhenEmailExistsThenExceptionThrown() {
        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        assertThrows(BadRequestException.class, () -> authService.assertEmailUnique("email"));

        verify(userRepository, times(1)).existsByEmail(anyString());
    }

    @Test
    public void testDeleteUserWhenKnownUserIdThenUserDeleted() {
        when(userRepository.deleteUserById(any(UUID.class))).thenReturn(1);

        authService.deleteUser(userId);

        verify(userRepository, times(1)).deleteUserById(any(UUID.class));
    }
}
