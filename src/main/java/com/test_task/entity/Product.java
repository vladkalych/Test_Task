package com.test_task.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "product")
@NoArgsConstructor
public class Product {

    public Product(Long id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Getter
    @Setter
    @ManyToOne(cascade = CascadeType.ALL)
    private Shop shop;



}
