package org.com.demoeventhubreceiver;

import com.azure.messaging.servicebus.ServiceBusProcessorClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoServiceBusReceiverApplication implements CommandLineRunner {
    private final ServiceBusProcessorClient serviceBusProcessorClient;

    public DemoServiceBusReceiverApplication(ServiceBusProcessorClient serviceBusProcessorClient) {
        this.serviceBusProcessorClient = serviceBusProcessorClient;
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoServiceBusReceiverApplication.class, args);
    }

    @Override
    public void run(String... args) {
        serviceBusProcessorClient.start();
    }
}
