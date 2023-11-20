package org.aviatrip.identityservice.kafka.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import org.aviatrip.identityservice.enumeration.UserEventType;

import java.util.UUID;

@Getter
@Builder
public class UserEvent {

    @JsonProperty("user_id")
    private UUID userId;

    @JsonProperty("event_type")
    private UserEventType eventType;
}
