package org.com.demoeventhubreceiver.config;

import com.azure.spring.cloud.service.eventhubs.consumer.EventHubsErrorHandler;
import com.azure.spring.cloud.service.eventhubs.consumer.EventHubsRecordMessageListener;
import org.com.demoeventhubreceiver.model.Message;
import org.com.demoeventhubreceiver.repository.MessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
public class EventHubProcessorClientConfiguration {
    private static final Logger LOGGER = LoggerFactory.getLogger(EventHubProcessorClientConfiguration.class);
    private final MessageRepository messageRepository;

    public EventHubProcessorClientConfiguration(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Bean
    EventHubsRecordMessageListener processEvent() {
        return eventContext -> {
            LOGGER.info("Processing event from partition {} with sequence number {} with body: {}",
                    eventContext.getPartitionContext().getPartitionId(), eventContext.getEventData().getSequenceNumber(),
                    eventContext.getEventData().getBodyAsString());

            String body = eventContext.getEventData().getBodyAsString();
            Message newMessage = new Message();
            newMessage.setId(UUID.randomUUID().toString());
            newMessage.setMessage(body);

            messageRepository.save(newMessage)
                    .flatMap(saved -> eventContext.updateCheckpointAsync())
                    .subscribe();
        };
    }

    @Bean
    EventHubsErrorHandler processError() {
        return errorContext -> LOGGER.info("Error occurred in partition processor for partition {}",
                errorContext.getPartitionContext().getPartitionId(),
                errorContext.getThrowable());
    }
}
