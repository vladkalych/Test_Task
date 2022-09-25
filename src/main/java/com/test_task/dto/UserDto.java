package com.test_task.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

public class UserDto {

    @Size(min = 6, message = "Не меньше 6 символів")
    @Getter
    @Setter
    private String username;

    @Size(min = 6, message = "Не меньше 6 символів")
    @Getter
    @Setter
    private String password;

}
