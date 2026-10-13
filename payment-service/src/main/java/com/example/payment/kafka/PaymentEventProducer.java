package com.example.payment.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.example.payment.event.PagamentoAprovadoEvent;
import com.example.payment.event.PagamentoRecusadoEvent;

@Component 
public class PaymentEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public PaymentEventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publicarAprovado(PagamentoAprovadoEvent event){
        kafkaTemplate.send("pagamento-aprovado", String.valueOf(event.pedidoId()), event);
    }

    public void publicarRecusado(PagamentoRecusadoEvent event){
        kafkaTemplate.send("pagamento-recusado", String.valueOf(event.pedidoId()), event);
    }
    
}
