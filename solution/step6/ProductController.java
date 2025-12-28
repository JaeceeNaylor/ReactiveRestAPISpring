package com.example.products.api;

import com.example.products.domain.Product;
import com.example.products.repository.ProductRepository;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import io.micrometer.observation.annotation.Observed;
import io.micrometer.observation.Observation;

import java.time.Duration;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductRepository repo;

    public ProductController(ProductRepository repo) {
        this.repo = repo;
    }

    @Observed(name = "products.http.getAll")
    @GetMapping
    public Flux<Product> getAll() {
        return repo.findAll();
    }

    @Observed(name = "products.http.create")
    @PostMapping
    public Mono<Product> create(@RequestBody Product product) {
        return repo.save(product);
    }

    @GetMapping(value = "/stream", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<Product> streamAll() {
        return repo.findAll().delayElements(Duration.ofMillis(100)); // backpressure demo
    }

    @GetMapping("/db-observe")
    public Flux<Product> getAllObserved() {
        return Observation
            .createNotStarted("products.db.findAll", Observation.Context::new)
            .observe(() -> repo.findAll());
    }
}
