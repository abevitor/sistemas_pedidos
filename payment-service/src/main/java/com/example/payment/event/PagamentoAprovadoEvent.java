package com.example.payment.event;

import java.math.BigDecimal;

public record PagamentoAprovadoEvent(Long pedidoId, BigDecimal valor) {}
