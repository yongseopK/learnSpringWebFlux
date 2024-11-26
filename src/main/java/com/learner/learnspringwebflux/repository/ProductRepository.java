package com.learner.learnspringwebflux.repository;

import com.learner.learnspringwebflux.model.Product;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductRepository extends ReactiveCrudRepository<Product, Long> {

    Flux<Product> findByNameContainingIgnoreCase(String name);

    @Query("SELECT * FROM products WHERE price <= :maxPrice")
    Flux<Product> findByPriceLessThanEqual(Double maxPrice);

    Mono<Boolean> existsByName(String name);
}
