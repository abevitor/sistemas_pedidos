package com.example.inventory.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.example.inventory.event.EstoqueInsuficienteEvent;
import com.example.inventory.event.EstoqueReservadoEvent;

@Component 
public class InventoryEventProducer {
    
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public InventoryEventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publicarReservado(EstoqueReservadoEvent event) {
        kafkaTemplate.send("estoque-reservado", String.valueOf(event.pedidoId()), event);
    }

     public void publicarInsuficiente(EstoqueInsuficienteEvent event) {
        kafkaTemplate.send("estoque-insuficiente", String.valueOf(event.pedidoId()), event);
    }
}
