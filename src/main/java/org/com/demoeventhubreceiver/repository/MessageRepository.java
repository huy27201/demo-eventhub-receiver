package org.com.demoeventhubreceiver.repository;

import com.azure.spring.data.cosmos.repository.ReactiveCosmosRepository;
import org.com.demoeventhubreceiver.model.Message;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends ReactiveCosmosRepository<Message, String> {}
