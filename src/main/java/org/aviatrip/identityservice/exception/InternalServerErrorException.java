package org.aviatrip.identityservice.exception;

import org.aviatrip.identityservice.dto.response.error.ErrorResponse;

import java.util.Optional;

public class InternalServerErrorException extends RuntimeException {

    ErrorResponse errorResponse;

    public InternalServerErrorException() {
    }

    public InternalServerErrorException(ErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
    }

    public Optional<ErrorResponse> getErrorResponse() {
        return Optional.ofNullable(errorResponse);
    }
}
