package com.example.inventory.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.example.inventory.event.PagamentoRecusadoEvent;
import com.example.inventory.event.PedidoCriadoEvent;
import com.example.inventory.service.EstoqueService;

@Component 
public class InventoryConsumer {

    private final EstoqueService service;

    public InventoryConsumer(EstoqueService service) {
        this.service = service;
    }

    @KafkaListener(
        topics = "pedido-criado",
        groupId = "inventory-service",
        properties = "spring.json.value.default.type=com.example.inventory.event.PedidoCriadoEvent"
    )
    public void aoCriarPedido(PedidoCriadoEvent event) {
        System.out.println("Pedido recebido: " + event);
        service.reservar(event);
    }

    @KafkaListener(
        topics = "pagamento-recusado",
        groupId = "inventory-service",
        properties = "spring.json.value.default.type=com.example.inventory.event.PagamentoRecusadoEvent"
    )
    public void aoRecusarPagamento(PagamentoRecusadoEvent event) {
        System.out.println("Pagamento recusado recebido: " + event);
        service.liberar(event.pedidoId());
    }


    
}
