package com.example.payment.kafka;


import com.example.payment.event.PagamentoRecusadoEvent;
import com.example.payment.event.PedidoCriadoEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PagamentoDltConsumer {

    private final PaymentEventProducer producer;

    public PagamentoDltConsumer(PaymentEventProducer producer) {
        this.producer = producer;
    }

    @KafkaListener(topics = "estoque-reservado.DLT", groupId = "payment-service-dlt")
    public void aoFalharDeVez(PedidoCriadoEvent event) {
        System.out.println("DLT: pagamento do pedido " + event.pedidoId() + "falhou de vez, recusando para compensar");
        producer.publicarRecusado( new PagamentoRecusadoEvent(event.pedidoId(), "Falha ao processar o pagamento"));
    }
    
}
