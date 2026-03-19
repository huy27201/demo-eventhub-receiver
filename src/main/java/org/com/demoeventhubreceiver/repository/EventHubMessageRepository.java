package org.com.demoeventhubreceiver.repository;

import com.azure.spring.data.cosmos.repository.ReactiveCosmosRepository;
import org.com.demoeventhubreceiver.model.EventHubMessage;
import org.springframework.stereotype.Repository;

@Repository
public interface EventHubMessageRepository extends ReactiveCosmosRepository<EventHubMessage, String> {}
