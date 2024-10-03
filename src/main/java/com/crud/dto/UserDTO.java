package com.crud.dto;

public record UserDTO(
    Long id,
    String email,
    String status,
    String role) {
}
