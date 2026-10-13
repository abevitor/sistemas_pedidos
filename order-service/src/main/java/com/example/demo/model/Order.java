package com.example.demo.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String clienteId;
    private BigDecimal valorTotal;

    @Enumerated
    private OrderStatus status;

    protected Order() {
    }

    public Order(String clienteId, BigDecimal valorTotal) {
        this.clienteId = clienteId;
        this.valorTotal = valorTotal;
        this.status = OrderStatus.PENDENTE;
    }

    public Long getId() {
        return id;
    }

    public String getClienteId() {
        return clienteId;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

}
