package com.example.inventory.model;

import jakarta.persistence.*;

@Entity
@Table(name = "reservas")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private Long pedidoId;

    private Long produtoId;
    private int quantidade;

    @Enumerated(EnumType.STRING)
    private ReservaStatus status;

    protected Reserva() {
    }

    public Reserva(Long pedidoId, Long produtoId, int quantidade) {
        this.pedidoId = pedidoId;
        this.produtoId = produtoId;
        this.quantidade = quantidade;
        this.status = ReservaStatus.RESERVADA;
    }

    public Long getId() {
        return id;
    }

    public Long getPedidoId() {
        return pedidoId;
    }

    public Long getProdutoId() {
        return produtoId;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public ReservaStatus getStatus() {
        return status;
    }

    public void setStatus(ReservaStatus status) {
        this.status = status;
    }

}
