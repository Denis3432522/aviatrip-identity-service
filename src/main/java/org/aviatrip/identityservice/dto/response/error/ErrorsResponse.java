package org.aviatrip.identityservice.dto.response.error;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;

@Builder
@Getter
@AllArgsConstructor
@ToString
public class ErrorsResponse {

    @JsonProperty("error_messages")
    private Map<String, String> errorMessages;

    @JsonProperty("details")
    private String details;
}