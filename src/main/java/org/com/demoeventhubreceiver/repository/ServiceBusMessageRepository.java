package org.com.demoeventhubreceiver.repository;

import com.azure.spring.data.cosmos.repository.ReactiveCosmosRepository;
import org.com.demoeventhubreceiver.model.ServiceBusMessage;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceBusMessageRepository extends ReactiveCosmosRepository<ServiceBusMessage, String> {}
