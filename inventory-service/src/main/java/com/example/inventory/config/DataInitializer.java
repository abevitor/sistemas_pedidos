package com.example.inventory.config;

import java.math.BigDecimal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.inventory.model.Produto;
import com.example.inventory.repository.ProdutoRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    private final ProdutoRepository repository;

    public DataInitializer(ProdutoRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) {
        if (repository.count() == 0) {
            repository.save(new Produto("Notebook", new BigDecimal("2500.00"), 10));  // id 1
            repository.save(new Produto("Mouse", new BigDecimal("80.00"), 50));       // id 2
            repository.save(new Produto("Teclado", new BigDecimal("200.00"), 5));     // id 3
        }
    }
}
