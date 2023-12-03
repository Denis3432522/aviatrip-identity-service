package org.aviatrip.identityservice.kafka.consumer;

// ,
//            properties = "spring.json.value.default.type=org.aviatrip.representativeservice.kafka.event.RepresentativeUserEvent")

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aviatrip.identityservice.enumeration.Role;
import org.aviatrip.identityservice.kafka.event.RepresentativeEvent;
import org.aviatrip.identityservice.service.AuthService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import static org.aviatrip.identityservice.enumeration.RepresentativeEventType.VERIFIED;

@Component
@Validated
@RequiredArgsConstructor
@Slf4j
public class RepresentativeListener {

    private final AuthService authService;

    @KafkaListener(groupId = "${kafka.consumer.representative.main-groupId}",
            topics = "${kafka.consumer.representative.main-topic}",
            containerFactory = "mainRepresentativeListenerContainerFactory",
            properties = "spring.json.value.default.type=org.aviatrip.identityservice.kafka.event.RepresentativeEvent"
    )
    public void handleRepresentativeEvent(@Payload @Valid RepresentativeEvent event) {
        if (event.getEnumEventType().equals(VERIFIED)) {
            log.debug(">>> Representative verification started: {}", event);
            authService.updateRole(event.getUserId(), Role.ROLE_VERIFIED_REPRESENTATIVE);
            log.debug("<<< Representative verified: {}", event);
        }

    }
}
