package com.example.demo.outbox;


import org.springframework.kafka.support.serializer.JacksonJsonSerializer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;

@Service
public class OutboxService {

    private final OutboxEventRepository repository;

    private final JacksonJsonSerializer<Object> serializer = new JacksonJsonSerializer<>();

     public OutboxService(OutboxEventRepository repository) {
        this.repository = repository;
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void registrar(String topic, String chave, Object evento) {
        String json = new String(serializer.serialize(topic, evento), StandardCharsets.UTF_8);
        repository.save(new OutboxEvent(topic, chave, json));
    }
    
}
