package org.com.demoeventhubreceiver;

import com.azure.messaging.eventhubs.EventProcessorClient;
import com.azure.messaging.servicebus.ServiceBusProcessorClient;
import org.com.demoeventhubreceiver.repository.ServiceBusMessageRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
class DemoReceiverApplicationTests {

    @MockitoBean
    private ServiceBusProcessorClient serviceBusProcessorClient;

    @MockitoBean
    private EventProcessorClient eventProcessorClient;

    @MockitoBean
    ServiceBusMessageRepository messageRepository;

    @Test
    void contextLoads() {
    }

}
