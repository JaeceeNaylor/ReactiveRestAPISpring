package com.example.products.api;

import com.example.products.domain.Product;
import com.example.products.repository.ProductRepository;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
@RequestMapping("/api/v2/products")
public class ProductControllerV2 {

    private final ProductRepository repo;

    public ProductControllerV2(ProductRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public Flux<Product> getAll() {
        return repo.findAll();
    }

    @PostMapping
    public Mono<Product> create(@RequestBody Product product) {
        return repo.save(product);
    }

    @GetMapping(value = "/stream", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<Product> streamAll() {
        return repo.findAll().delayElements(Duration.ofMillis(100));
    }

    @GetMapping("/{id}")
    public Mono<Product> getById(@PathVariable Long id) {
        return repo.findById(id);
    }

    @GetMapping("/search")
    public Flux<Product> search(@RequestParam String name) {
        return repo.findAll().filter(p -> p.getName().toLowerCase().contains(name.toLowerCase()));
    }
}
