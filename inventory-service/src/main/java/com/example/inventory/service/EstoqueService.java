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
    }
    
    
}
