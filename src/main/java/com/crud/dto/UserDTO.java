package com.crud.dto;

import com.crud.model.user.UserAuthority;
import com.crud.model.user.UserProvider;
import com.crud.model.user.UserStatus;

public record UserDTO(
    Long id,
    String name,
    String email,
    UserStatus status,
    UserProvider provider,
    UserAuthority authority,
    String imageUrl,
    String token) {
}