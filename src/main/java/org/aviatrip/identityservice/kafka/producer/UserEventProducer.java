package org.aviatrip.identityservice.kafka.producer;

import lombok.RequiredArgsConstructor;
import org.aviatrip.identityservice.enumeration.Role;
import org.aviatrip.identityservice.kafka.event.UserEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${kafka.producer.customer-user.topic}")
    private String customerUserTopic;

    @Value("${kafka.producer.representative-user.topic}")
    private String representativeUserTopic;

    public void sendUserEvent(UserEvent event, Role role) {
        if(Role.ROLE_CUSTOMER.equals(role))
            sendCustomerUserEvent(event);
        else if(Role.ROLE_REPRESENTATIVE.equals(role))
            sendRepresentativeUserEvent(event);
    }

    public void sendCustomerUserEvent(UserEvent event) {
        kafkaTemplate.send(customerUserTopic,  event);
    }

    public void sendRepresentativeUserEvent(UserEvent event) {
        kafkaTemplate.send(representativeUserTopic, event);
    }
}
