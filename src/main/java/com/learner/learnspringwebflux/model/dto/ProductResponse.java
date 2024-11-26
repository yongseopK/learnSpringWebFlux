package com.learner.learnspringwebflux.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private LocalDateTime createdAt;
}
