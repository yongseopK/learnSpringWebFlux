package com.learner.learnspringwebflux.service;


import com.learner.learnspringwebflux.model.dto.ProductRequest;
import com.learner.learnspringwebflux.model.dto.ProductResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductService {
    Flux<ProductResponse> findAll();
    Mono<ProductResponse> findById(Long id);
    Mono<ProductResponse> create(ProductRequest request);
    Mono<ProductResponse> update(Long id, ProductRequest request);
    Mono<Void> delete(Long id);
    Flux<ProductResponse> findByNameContaining(String name);
}
