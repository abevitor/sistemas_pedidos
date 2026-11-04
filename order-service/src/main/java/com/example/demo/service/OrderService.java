package com.example.demo.service;

import com.example.demo.client.InventoryClient;
import com.example.demo.dto.CreateOrderRequest;
import com.example.demo.dto.ProdutoDto;
import com.example.demo.event.PedidoCriadoEvent;
import com.example.demo.model.Order;
import com.example.demo.outbox.OutboxService;
import com.example.demo.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class OrderService {

    private final OrderRepository repository;
    private final OutboxService outbox;
    private final InventoryClient inventoryClient;

    public OrderService(OrderRepository repository, OutboxService outbox,
                        InventoryClient inventoryClient) {
        this.repository = repository;
        this.outbox = outbox;
        this.inventoryClient = inventoryClient;
    }

    @Transactional
    public Order criar(String clienteId, CreateOrderRequest request) {
        // busca o preço real no inventory-service; nunca confia em valor vindo do cliente
        ProdutoDto produto = inventoryClient.buscarProduto(request.produtoId());
        BigDecimal valorTotal = produto.preco().multiply(BigDecimal.valueOf(request.quantidade()));

        Order order = repository.save(new Order(
            clienteId, request.produtoId(), request.quantidade(), valorTotal));

        PedidoCriadoEvent event = new PedidoCriadoEvent(
            order.getId(), order.getClienteId(), order.getProdutoId(),
            order.getQuantidade(), order.getValorTotal());

        outbox.registrar("pedido-criado", String.valueOf(order.getId()), event);

        return order;
    }
}