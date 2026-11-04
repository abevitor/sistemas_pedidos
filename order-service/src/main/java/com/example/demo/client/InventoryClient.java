package com.example.demo.client;

import com.example.demo.dto.ProdutoDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@Component
public class InventoryClient {

    private final RestClient restClient;

    public InventoryClient(@Value("${inventory.service.url}") String baseUrl) {
        this.restClient = RestClient.builder().baseUrl(baseUrl).build();
    }

    public ProdutoDto buscarProduto(Long produtoId) {
        try {
            return restClient.get()
                    .uri("/produtos/{id}", produtoId)
                    .retrieve()
                    .body(ProdutoDto.class);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Produto " + produtoId + " não encontrado");
        }
    }
}
