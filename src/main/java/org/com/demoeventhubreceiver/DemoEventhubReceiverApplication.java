package org.com.demoeventhubreceiver;

import com.azure.messaging.eventhubs.EventProcessorClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoEventhubReceiverApplication implements CommandLineRunner {
    private final EventProcessorClient eventProcessorClient;

    public DemoEventhubReceiverApplication(EventProcessorClient eventProcessorClient) {
        this.eventProcessorClient = eventProcessorClient;
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoEventhubReceiverApplication.class, args);
    }

    @Override
    public void run(String... args) {
        eventProcessorClient.start();
    }
}
