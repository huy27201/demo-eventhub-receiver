package org.com.demoeventhubreceiver;

import com.azure.messaging.servicebus.ServiceBusProcessorClient;
import org.com.demoeventhubreceiver.repository.MessageRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
class DemoEventhubReceiverApplicationTests {

    @MockitoBean
    private ServiceBusProcessorClient serviceBusProcessorClient;

    @MockitoBean
    MessageRepository messageRepository;

    @Test
    void contextLoads() {
    }

}
