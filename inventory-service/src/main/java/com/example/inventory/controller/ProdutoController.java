package com.example.inventory.controller;

import com.example.inventory.model.Produto;
import com.example.inventory.repository.ProdutoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoRepository repository;

    public ProdutoController(ProdutoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Produto> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscar(@PathVariable("id") Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
