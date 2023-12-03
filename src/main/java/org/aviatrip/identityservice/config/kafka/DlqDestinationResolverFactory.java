package org.aviatrip.identityservice.config.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;
import org.springframework.stereotype.Component;

import java.util.function.BiFunction;

@Component
@Slf4j
public class DlqDestinationResolverFactory {

    public BiFunction<ConsumerRecord<?, ?>, Exception, TopicPartition> createDefaultRecoverer(String dlqTopic) {
        return (record, ex) -> {
            Throwable thrownException = ex.getCause() == null ? ex : ex.getCause();
            log.error("Exception [{}] occurred with reason [{}] sending the record to the topic [{}]",
                    thrownException.getClass().getSimpleName(), thrownException.getMessage(), dlqTopic);

            return new TopicPartition(dlqTopic, -1);
        };
    }
}
