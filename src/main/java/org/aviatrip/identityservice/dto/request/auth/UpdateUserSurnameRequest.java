package org.aviatrip.identityservice.dto.request.auth;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record UpdateUserSurnameRequest(
        @NotNull @Pattern(regexp = "[A-Za-z]{2,54}", message = "{messages.user.surname}") String surname) {}
