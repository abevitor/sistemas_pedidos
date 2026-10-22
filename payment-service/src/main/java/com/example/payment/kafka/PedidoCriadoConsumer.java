package com.example.payment.kafka;

import java.math.BigDecimal;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.example.payment.event.PagamentoAprovadoEvent;
import com.example.payment.event.PagamentoRecusadoEvent;
import com.example.payment.event.PedidoCriadoEvent;

@Component
public class PedidoCriadoConsumer {

    private final PaymentEventProducer producer;

    public PedidoCriadoConsumer(PaymentEventProducer producer) {
        this.producer = producer;
    }

    @KafkaListener(topics = "estoque-reservado", groupId = "payment-service")
    public void consumir(PedidoCriadoEvent event) {
        System.out.println("Pedido recebido: " + event);

        boolean aprovado = ThreadLocalRandom.current().nextInt(100) < 70;

        if (aprovado) {
            producer.publicarAprovado(new PagamentoAprovadoEvent(event.pedidoId(), event.valorTotal()));
            System.out.println("Pagamento APROVADO do pedido " + event.pedidoId());
        } else {
            producer.publicarRecusado(new PagamentoRecusadoEvent(event.pedidoId(), "Saldo insuficiente"));
            System.out.println("Pagamento RECUSADO do pedido " + event.pedidoId());
        }
    }

}
