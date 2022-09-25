package com.test_task.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

public class ProductDto {

    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    @Size(min = 5, message = "Мінімум 5 символів")
    private String name;

    @Getter
    @Setter
    @Size(min = 10, message = "Мінімум 10 символів")
    private String description;

    @Getter
    @Setter
    private Double price;

}
