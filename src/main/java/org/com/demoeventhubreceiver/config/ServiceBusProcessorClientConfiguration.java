package org.com.demoeventhubreceiver.config;

import com.azure.messaging.servicebus.ServiceBusReceivedMessage;
import com.azure.spring.cloud.service.servicebus.consumer.ServiceBusErrorHandler;
import com.azure.spring.cloud.service.servicebus.consumer.ServiceBusRecordMessageListener;
import org.com.demoeventhubreceiver.model.ServiceBusMessage;
import org.com.demoeventhubreceiver.repository.ServiceBusMessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.UUID;

@Configuration(proxyBeanMethods = false)
public class ServiceBusProcessorClientConfiguration {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceBusProcessorClientConfiguration.class);
    private final ServiceBusMessageRepository messageRepository;

    public ServiceBusProcessorClientConfiguration(ServiceBusMessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Bean
    ServiceBusRecordMessageListener processMessage() {
        return context -> {
            ServiceBusReceivedMessage message = context.getMessage();
            LOGGER.info("Processing message. Id: {}, Sequence #: {}. Contents: {}", message.getMessageId(),
                    message.getSequenceNumber(), message.getBody());

            String body =  message.getBody().toString();
            ServiceBusMessage newMessage = new ServiceBusMessage();
            newMessage.setId(UUID.randomUUID().toString());
            newMessage.setBody(body);

            messageRepository.save(newMessage)
                    .timeout(Duration.ofSeconds(10))
                    .doOnError(ex -> LOGGER.error(
                            "Failed to persist message. Persisted id: {}, Service Bus message id: {}",
                            newMessage.getId(), message.getMessageId(), ex))
                    .block();
        };
    }

    @Bean
    ServiceBusErrorHandler processServiceBusError() {
        return context -> {
            LOGGER.error(
                    "Error when receiving messages from namespace: '{}'. Entity: '{}'",
                    context.getFullyQualifiedNamespace(),
                    context.getEntityPath(),
                    context.getException());
        };
    }
}
