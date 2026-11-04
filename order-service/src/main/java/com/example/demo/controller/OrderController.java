package com.example.demo.controller;

import com.example.demo.dto.CreateOrderRequest;
import com.example.demo.model.Order;
import com.example.demo.repository.OrderRepository;
import com.example.demo.service.OrderService;
import com.example.demo.sse.OrderStatusStream;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderRepository repository;
    private final OrderService service;
    private final OrderStatusStream statusStream;

    public OrderController(OrderRepository repository, OrderService service,
                           OrderStatusStream statusStream) {
        this.repository = repository;
        this.service = service;
        this.statusStream = statusStream;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Order criar(@AuthenticationPrincipal Jwt jwt, @RequestBody CreateOrderRequest request) {
        String clienteId = jwt.getSubject(); // o ID do usuário, extraído do token
        return service.criar(clienteId, request);
    }

    @GetMapping
    public List<Order> listar() {
        return repository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    @GetMapping(path = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter stream() {
        return statusStream.inscrever();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> buscar(@PathVariable("id") Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}