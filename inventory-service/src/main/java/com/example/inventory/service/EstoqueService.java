package com.example.inventory.service;

import com.example.inventory.event.EstoqueInsuficienteEvent;
import com.example.inventory.event.EstoqueReservadoEvent;
import com.example.inventory.event.PedidoCriadoEvent;
import com.example.inventory.kafka.InventoryEventProducer;
import com.example.inventory.model.Produto;
import com.example.inventory.model.Reserva;
import com.example.inventory.model.ReservaStatus;
import com.example.inventory.repository.ProdutoRepository;
import com.example.inventory.repository.ReservaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EstoqueService {

    private final ProdutoRepository produtoRepository;
    private final ReservaRepository reservaRepository;
    private final InventoryEventProducer producer;

    public EstoqueService(ProdutoRepository produtoRepository,
            ReservaRepository reservaRepository,
            InventoryEventProducer producer) {
        this.produtoRepository = produtoRepository;
        this.reservaRepository = reservaRepository;
        this.producer = producer;
    }

    @Transactional
    public void reservar(PedidoCriadoEvent event) {
        if(event.produtoId() == null || event.quantidade() == null ) {
            System.out.println("Pedido " + event.pedidoId() + "sem produto/quantidade, ignorando");
            return;
        }

        if(reservaRepository.existsByPedidoId(event.pedidoId())) {
            System.out.println("Pedido " + event.pedidoId() + " já tem reserva, ignorando");
            return;
        }

        Produto produto = produtoRepository.buscarComLock(event.produtoId()).orElse(null);

    if(produto == null) {
        producer.publicarInsuficiente(
            new EstoqueInsuficienteEvent(event.pedidoId(), "Produto não encontrado"));
            System.out.println("Pedido " + event.pedidoId() + ": produto não encontrado");
            return;
    }

    if (produto.getQuantidadeDisponivel() < event.quantidade()) {
            producer.publicarInsuficiente(
                new EstoqueInsuficienteEvent(event.pedidoId(), "Estoque insuficiente"));
            System.out.println("Pedido " + event.pedidoId() + ": estoque insuficiente");
            return;
        }

        produto.setQuantidadeDisponivel((produto.getQuantidadeDisponivel() - event.quantidade()));
        produtoRepository.save(produto);
        reservaRepository.save(new Reserva(event.pedidoId(), event.produtoId(), event.quantidade()));

        producer.publicarReservado(
            new EstoqueReservadoEvent(event.pedidoId(), event.clienteId(), event.valorTotal()));
            System.out.println("Estoque RESERVADO para o pedido " + event.pedidoId());
    }

    @Transactional
    public void liberar(Long pedidoId) {
        Reserva reserva = reservaRepository.findByPedidoId(pedidoId).orElse(null);

        if (reserva == null || reserva.getStatus() != ReservaStatus.RESERVADA) {
            System.out.println("Pedido " + pedidoId + ": nada para liberar.");
            return;
        }

        Produto produto = produtoRepository.buscarComLock(reserva.getProdutoId()).orElseThrow();
        produto.setQuantidadeDisponivel(produto.getQuantidadeDisponivel() + reserva.getQuantidade());
        produtoRepository.save(produto);

        reserva.setStatus(ReservaStatus.LIBERADA);
        reservaRepository.save(reserva);
        System.out.println("Estoque DEVOLVIDO do pedido " + pedidoId);
    }
}
