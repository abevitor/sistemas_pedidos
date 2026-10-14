package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.ResponseEntity;

import com.example.demo.Repository.OrderRepository;
import com.example.demo.dto.CreateOrderRequest;
import com.example.demo.event.PedidoCriadoEvent;
import com.example.demo.kafka.OrderEventProducer;
import com.example.demo.model.Order;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderRepository repository;
    private final OrderEventProducer producer;

    public OrderController(OrderRepository repository, OrderEventProducer producer) {
        this.repository = repository;
        this.producer = producer;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Order criar(@RequestBody CreateOrderRequest request) {
        Order order = repository.save(new Order(
            request.clienteId(), request.produtoId(), request.quantidade(), request.valorTotal()));

        producer.publicarPedidoCriado(new PedidoCriadoEvent(
            order.getId(), order.getClienteId(), order.getProdutoId(),
            order.getQuantidade(), order.getValorTotal()));

        return order;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> buscar(@PathVariable("id") Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
