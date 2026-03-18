package org.com.demoeventhubreceiver.config;

import com.azure.messaging.servicebus.ServiceBusReceivedMessage;
import com.azure.spring.cloud.service.servicebus.consumer.ServiceBusErrorHandler;
import com.azure.spring.cloud.service.servicebus.consumer.ServiceBusRecordMessageListener;
import org.com.demoeventhubreceiver.model.Message;
import org.com.demoeventhubreceiver.repository.MessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.UUID;

@Configuration(proxyBeanMethods = false)
public class ServiceBusProcessorClientConfiguration {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceBusProcessorClientConfiguration.class);
    private final MessageRepository messageRepository;

    public ServiceBusProcessorClientConfiguration(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

//    @Bean
//    EventHubsRecordMessageListener processEvent() {
//        return eventContext -> {
//            LOGGER.info("Processing event from partition {} with sequence number {} with body: {}",
//                    eventContext.getPartitionContext().getPartitionId(), eventContext.getEventData().getSequenceNumber(),
//                    eventContext.getEventData().getBodyAsString());
//
//            String body = eventContext.getEventData().getBodyAsString();
//            Message newMessage = new Message();
//            newMessage.setId(UUID.randomUUID().toString());
//            newMessage.setMessage(body);
//
//            messageRepository.save(newMessage)
//                    .flatMap(saved -> eventContext.updateCheckpointAsync())
//                    .subscribe();
//        };
//    }

    @Bean
    ServiceBusRecordMessageListener processMessage() {
        return context -> {
            ServiceBusReceivedMessage message = context.getMessage();
            LOGGER.info("Processing message. Id: {}, Sequence #: {}. Contents: {}", message.getMessageId(),
                    message.getSequenceNumber(), message.getBody());

            String body =  message.getBody().toString();
            Message newMessage = new Message();
            newMessage.setId(UUID.randomUUID().toString());
            newMessage.setMessage(body);

            messageRepository.save(newMessage)
                    .timeout(Duration.ofSeconds(10))
                    .doOnError(ex -> LOGGER.error(
                            "Failed to persist message id: {}", message.getMessageId(), ex))
                    .block();
        };
    }

    @Bean
    ServiceBusErrorHandler processError() {
        return context -> {
            LOGGER.error(
                    "Error when receiving messages from namespace: '{}'. Entity: '{}'",
                    context.getFullyQualifiedNamespace(),
                    context.getEntityPath(),
                    context.getException());
        };
    }
}
