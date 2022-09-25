package com.test_task.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

public class ShopDto {

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

}
