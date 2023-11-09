package org.aviatrip.identityservice.dto.request.auth;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record UpdateUserNameRequest(
        @NotNull @Pattern(regexp = "[A-Za-z]{2,32}", message = "{messages.user.name}") String name) {}
