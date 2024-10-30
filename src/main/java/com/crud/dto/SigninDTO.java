package com.crud.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SigninDTO(
        @NotNull @NotBlank @Email @Length(min = 3, max = 100) String email,
        @NotNull @NotBlank @Length(min = 8, max = 100) String password) {

}
