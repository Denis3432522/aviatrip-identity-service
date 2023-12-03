package org.aviatrip.identityservice.kafka.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.aviatrip.identityservice.enumeration.RepresentativeEventType;
import org.aviatrip.identityservice.validation.annotation.EnumString;

import java.util.UUID;

@Getter
@Setter
@ToString
public class RepresentativeEvent {

    @JsonProperty("user_id")
    @NotNull
    private UUID userId;

    @JsonProperty("event_type")
    @NotNull
    @EnumString(enumClazz = RepresentativeEventType.class)
    private String eventType;

    @JsonIgnore
    public RepresentativeEventType getEnumEventType() {
        return RepresentativeEventType.valueOf(eventType);
    }
}
