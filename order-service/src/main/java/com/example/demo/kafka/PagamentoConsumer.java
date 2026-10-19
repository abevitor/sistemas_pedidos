package com.example.demo.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.example.demo.event.PagamentoAprovadoEvent;
import com.example.demo.event.PagamentoRecusadoEvent;
import com.example.demo.model.Order;
import com.example.demo.model.OrderStatus;
import com.example.demo.repository.OrderRepository;
import com.example.demo.event.EstoqueInsuficienteEvent;

import jakarta.transaction.Transactional;

@Component 
public class PagamentoConsumer {
    
    private final OrderRepository repository;

    public PagamentoConsumer(OrderRepository repository){
        this.repository = repository;
    }

    @KafkaListener(
        topics = "pagamento-aprovado",
        groupId = "order-service",
        properties = "spring.json.value.default.type=com.example.demo.event.PagamentoAprovadoEvent"
    )
    @Transactional 
    public void aoAprovar(PagamentoAprovadoEvent event) {
        System.out.println("Pagamento aprovado recebido: " + event);
        atualizarStatus(event.pedidoId(), OrderStatus.CONFIRMADO);
    }

    @KafkaListener (
        topics = "pagamento-recusado",
        groupId = "order-service",
        properties = "spring.json.value.default.type=com.example.demo.event.PagamentoRecusadoEvent"
    )
    @Transactional
    public void aoRecusar(PagamentoRecusadoEvent event) {
        System.out.println("Pagamento recusado recebido: " + event);
        atualizarStatus(event.pedidoId(), OrderStatus.CANCELADO);
    } 

    @KafkaListener(
        topics = "estoque-insuficiente",
        groupId = "order-service",
        properties = "spring.json.value.default.type=com.example.demo.event.EstoqueInsuficienteEvent"
    )
    @Transactional 
    public void aoFaltarEstoque(EstoqueInsuficienteEvent event) {
        System.out.println("Estoque insuficiente recebido: " + event);
        atualizarStatus(event.pedidoId(), OrderStatus.CANCELADO);
    }

    private void atualizarStatus(Long pedidoId, OrderStatus novoStatus) {
        Order order = repository.findById(pedidoId).orElse(null);

        if(order == null) {
            System.out.println("Pedido " + pedidoId + "não encontrado, ignorando.");
            return;
        }

        if(order.getStatus() != OrderStatus.PENDENTE) {
            System.out.println("Pedido " + pedidoId + " já está " + order.getStatus() + ", ignorando");
            return;
        }

        order.setStatus(novoStatus);
        repository.save(order);
        System.out.println("Pedido " + pedidoId + " agora está " + novoStatus);
    }
    
}
