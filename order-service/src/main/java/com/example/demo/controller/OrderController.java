package com.example.demo.controller;

import com.example.demo.dto.CreateOrderRequest;
import com.example.demo.model.Order;
import com.example.demo.repository.OrderRepository;
import com.example.demo.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderRepository repository;
    private final OrderService service;

    public OrderController(OrderRepository repository, OrderService service) {
        this.repository = repository;
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Order criar(@RequestBody CreateOrderRequest request) {
        return service.criar(request);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> buscar(@PathVariable("id") Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}