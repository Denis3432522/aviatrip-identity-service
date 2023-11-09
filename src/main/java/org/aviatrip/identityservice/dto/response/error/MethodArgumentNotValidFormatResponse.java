package org.aviatrip.identityservice.dto.response.error;


public class MethodArgumentNotValidFormatResponse extends ErrorResponse {

    private static final String DEFAULT_MESSAGE = "invalid request format";

    public MethodArgumentNotValidFormatResponse() {
        super(DEFAULT_MESSAGE, null);
    }
}
