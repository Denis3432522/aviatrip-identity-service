package org.aviatrip.identityservice.dto.request.auth;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class AuthRequest {
    @NotNull
    private String email;

    @NotNull
    private String password;
}
