package com.example.demo.event;

import java.math.BigDecimal;

public record PagamentoAprovadoEvent(Long pedidoId, BigDecimal valor) {}
