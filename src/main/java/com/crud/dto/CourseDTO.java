package com.crud.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CourseDTO(
    Long id,
    @NotBlank @NotNull @Length(min = 3, max = 100) String name,
    @NotNull @Length(max = 20) String category){
}
