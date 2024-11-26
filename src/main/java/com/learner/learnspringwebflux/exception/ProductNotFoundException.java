package com.learner.learnspringwebflux.exception;

public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException(Long id) {
        super("Product not found with id: " + id);
    }
}
