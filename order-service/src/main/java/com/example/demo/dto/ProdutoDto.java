package com.example.demo.dto;

import java.math.BigDecimal;

public record ProdutoDto(Long id, String nome, BigDecimal preco, int quantidadeDisponivel) {}