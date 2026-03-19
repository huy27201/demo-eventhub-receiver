package org.com.demoeventhubreceiver;

import com.azure.messaging.eventhubs.EventProcessorClient;
import com.azure.messaging.servicebus.ServiceBusProcessorClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoReceiverApplication implements CommandLineRunner {
    private final ServiceBusProcessorClient serviceBusProcessorClient;
    private final EventProcessorClient eventProcessorClient;

    public DemoReceiverApplication(EventProcessorClient eventProcessorClient, ServiceBusProcessorClient serviceBusProcessorClient) {
        this.serviceBusProcessorClient = serviceBusProcessorClient;
        this.eventProcessorClient = eventProcessorClient;
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoReceiverApplication.class, args);
    }

    @Override
    public void run(String... args) {
        serviceBusProcessorClient.start();
        eventProcessorClient.start();
    }
}
