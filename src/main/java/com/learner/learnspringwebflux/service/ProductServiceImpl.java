package com.learner.learnspringwebflux.service;

import com.learner.learnspringwebflux.exception.ProductNotFoundException;
import com.learner.learnspringwebflux.model.Product;
import com.learner.learnspringwebflux.model.dto.ProductRequest;
import com.learner.learnspringwebflux.model.dto.ProductResponse;
import com.learner.learnspringwebflux.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Flux<ProductResponse> findAll() {
        return productRepository.findAll()
                .map(this::mapToProductResponse);
    }

    @Override
    public Mono<ProductResponse> findById(Long id) {
        return productRepository.findById(id)
                .map(this::mapToProductResponse)
                .switchIfEmpty(Mono.error(new ProductNotFoundException(id)));
    }

    @Override
    public Mono<ProductResponse> create(ProductRequest request) {
        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        return productRepository.save(product)
                .map(this::mapToProductResponse);
    }

    @Override
    public Mono<ProductResponse> update(Long id, ProductRequest request) {
        return productRepository.findById(id)
                .flatMap(existingProduct -> {
                    existingProduct.setName(request.getName());
                    existingProduct.setDescription(request.getDescription());
                    existingProduct.setPrice(request.getPrice());
                    existingProduct.setUpdatedAt(LocalDateTime.now());
                    return productRepository.save(existingProduct);
                })
                .map(this::mapToProductResponse)
                .switchIfEmpty(Mono.error(new ProductNotFoundException(id)));
    }

    @Override
    public Mono<Void> delete(Long id) {
        return productRepository.deleteById(id);
    }

    @Override
    public Flux<ProductResponse> findByNameContaining(String name) {
        return productRepository.findByNameContainingIgnoreCase(name)
                .map(this::mapToProductResponse);
    }

    private ProductResponse mapToProductResponse(Product product) {
        ProductResponse response = new ProductResponse();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setPrice(product.getPrice());
        response.setCreatedAt(product.getCreatedAt());
        return response;
    }
}
