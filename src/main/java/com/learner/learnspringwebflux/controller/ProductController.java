package com.learner.learnspringwebflux.controller;

import com.learner.learnspringwebflux.model.Product;
import com.learner.learnspringwebflux.model.dto.ProductRequest;
import com.learner.learnspringwebflux.model.dto.ProductResponse;
import com.learner.learnspringwebflux.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public Flux<ProductResponse> getProducts(@PathVariable Long id) {
        return productService.findAll();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<ProductResponse>> getProduct(@PathVariable Long id) {
        return productService.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public Flux<ProductResponse> searchProducts(@RequestParam String name) {
        return productService.findByNameContaining(name);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ProductResponse> createProduct(@Valid @RequestBody ProductRequest request) {
        return productService.create(request);
    }


    @PutMapping("/{id}")
    public Mono<ResponseEntity<ProductResponse>> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductRequest request) {
        return productService.update(id, request)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteProduct(@PathVariable Long id) {
        return productService.delete(id);
    }

    // Example Server-Sent Event
    @GetMapping(value = "/stream", produces = "text/event-stream")
    public Flux<ProductResponse> streamProducts() {
        return productService.findAll()
                .delayElements(java.time.Duration.ofSeconds(1));
    }

    @PostMapping("/batch")
    @ResponseStatus(HttpStatus.CREATED)
    public Flux<ProductResponse> createProducts(@Valid @RequestBody Flux<ProductRequest> requests) {
        return requests.flatMap(productService::create);
    }
}
