package org.aviatrip.identityservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aviatrip.identityservice.dto.request.UserRequest;
import org.aviatrip.identityservice.dto.request.auth.AuthRequest;
import org.aviatrip.identityservice.dto.request.auth.UpdateUserPasswordRequest;
import org.aviatrip.identityservice.enumeration.Role;
import org.aviatrip.identityservice.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@RequestBody @Valid UserRequest model) {
        authService.createUser(model);
    }

    @PostMapping("/token")
    public String generateToken(@RequestBody @Valid AuthRequest request) {

        Authentication authentication = authService.authenticate(request);
        Role role = Role.valueOf(authentication.getAuthorities().stream().findFirst().orElseThrow(RuntimeException::new).toString());

        return authService.generateToken(role, ((UserDetails) authentication.getPrincipal()).getUsername());
    }

    @GetMapping("/validate")
    public void validateToken(@RequestParam("token") String token) {
        authService.validate(token);
    }

    @PatchMapping("/password")
    public void updatePassword(@RequestBody @Valid UpdateUserPasswordRequest request,
                               @RequestHeader("subject") UUID userId) {

        authService.updatePassword(request.password(), userId);
    }

    @DeleteMapping("/user")
    public void deleteUser(@RequestHeader("subject") UUID userId) {
        authService.deleteUser(userId);
    }
}
