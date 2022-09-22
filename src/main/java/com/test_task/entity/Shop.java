package com.test_task.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "shop")
@NoArgsConstructor
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter
    @Getter
    private Long id;

    @Setter
    @Getter
    @Size(min = 8, message = "Мінімум 8 символів")
    private String name;

    @Setter
    @Getter
    @Size(min = 20, message = "Мінімум 20 символів")
    private String address;

    @Transient
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "shop")
    @Setter
    @Getter
    private Set<Product> products;
}
