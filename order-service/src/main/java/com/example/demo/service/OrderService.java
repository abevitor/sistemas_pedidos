package com.example.demo.service;

import com.example.demo.dto.CreateOrderRequest;
import com.example.demo.event.PedidoCriadoEvent;
import com.example.demo.model.Order;
import com.example.demo.outbox.OutboxService;
import com.example.demo.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    private final OrderRepository repository;
    private final OutboxService outbox;

    public OrderService(OrderRepository repository, OutboxService outbox) {
        this.repository = repository;
        this.outbox = outbox;
    }

    @Transactional 
    public Order criar (CreateOrderRequest request) {
        Order order = repository.save(new Order(request.clienteId(), request.produtoId(), request.quantidade(), request.valorTotal()));

        PedidoCriadoEvent event = new PedidoCriadoEvent(
            order.getId(), order.getClienteId(), order.getProdutoId(), order.getQuantidade(), order.getValorTotal()
        );

         outbox.registrar("pedido-criado", String.valueOf(order.getId()), event);

         return order;

    }
    
}
