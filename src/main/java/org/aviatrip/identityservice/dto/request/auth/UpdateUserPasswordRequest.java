package org.aviatrip.identityservice.dto.request.auth;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateUserPasswordRequest(@NotNull @Size(min=8, max = 120) String password) {}
