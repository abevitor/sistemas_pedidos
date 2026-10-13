package com.example.demo.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.example.demo.event.PedidoCriadoEvent;

@Component
public class OrderEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public OrderEventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publicarPedidoCriado(PedidoCriadoEvent event) {
        kafkaTemplate.send("pedido-criado", String.valueOf(event.pedidoId()), event);
    }
}
