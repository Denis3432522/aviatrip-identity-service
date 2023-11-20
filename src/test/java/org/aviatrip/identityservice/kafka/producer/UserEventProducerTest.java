package org.aviatrip.identityservice.kafka.producer;

import org.aviatrip.identityservice.enumeration.Role;
import org.aviatrip.identityservice.enumeration.UserEventType;
import org.aviatrip.identityservice.kafka.event.UserEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserEventProducerTest {

    @Mock
    private KafkaTemplate<String, Object> template;

    @InjectMocks
    private UserEventProducer userEventProducer;

    @Test
    public void testSendUserEventWhenRoleIsCustomerThenSendCustomerUserEvent() {
        UserEvent event = UserEvent.builder()
                .userId(UUID.randomUUID())
                .eventType(UserEventType.CREATED)
                .build();

        when(template.send(eq("main-customer-user"), any(UserEvent.class)))
                .thenReturn(null);

        userEventProducer.sendUserEvent(event, Role.ROLE_CUSTOMER);

        verify(template, times(1)).send("main-customer-user", event);
    }

    @Test
    public void testSendUserEventWhenRoleIsRepresentativeThenSendRepresentativeUserEvent() {
        UserEvent event = UserEvent.builder()
                .userId(UUID.randomUUID())
                .eventType(UserEventType.CREATED)
                .build();

        when(template.send(eq("main-representative-user"), any(UserEvent.class)))
                .thenReturn(null);

        userEventProducer.sendUserEvent(event, Role.ROLE_REPRESENTATIVE);

        verify(template, times(1)).send("main-representative-user", event);
    }

    @Test
    public void testSendCustomerUserEventThenSendToCustomerUserTopic() {
        UserEvent event = UserEvent.builder()
                .userId(UUID.randomUUID())
                .eventType(UserEventType.CREATED)
                .build();

        when(template.send(eq("main-customer-user"), any(UserEvent.class)))
                .thenReturn(null);

        userEventProducer.sendCustomerUserEvent(event);

        verify(template).send("main-customer-user", event);
    }

    @Test
    public void testSendRepresentativeUserEventThenSendToRepresentativeUserTopic() {
        UserEvent event = UserEvent.builder()
                .userId(UUID.randomUUID())
                .eventType(UserEventType.CREATED)
                .build();

        when(template.send(eq("main-representative-user"), any(UserEvent.class)))
                .thenReturn(null);

        userEventProducer.sendRepresentativeUserEvent(event);

        verify(template).send("main-representative-user", event);
    }
}
