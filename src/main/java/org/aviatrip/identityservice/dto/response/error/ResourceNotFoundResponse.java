package org.aviatrip.identityservice.dto.response.error;

public class ResourceNotFoundResponse extends ErrorResponse {

    private static final String MESSAGE_POSTFIX = " not found";
    private static final String DEFAULT_MESSAGE = "resource" + MESSAGE_POSTFIX;

    public ResourceNotFoundResponse() {
        super(DEFAULT_MESSAGE, null);
    }

    public ResourceNotFoundResponse(String resourceName) {
        super(resourceName + MESSAGE_POSTFIX, null);
    }

    public ResourceNotFoundResponse(String resourceName, String details) {
        super(resourceName + MESSAGE_POSTFIX, details);
    }
}
