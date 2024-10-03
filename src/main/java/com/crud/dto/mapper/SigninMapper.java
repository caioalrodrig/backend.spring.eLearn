// package com.crud.dto.mapper;

// import org.springframework.stereotype.Component;

// import com.crud.dto.SigninDTO;
// import com.crud.dto.UserDTO;
// import com.crud.model.User;

// @Component
// public class SigninMapper {

//   public User toEntity(UserDTO userDTO) {
//     if (userDTO == null) {
//       return null;
//     }

//     User user = new User();
//     if (userDTO.id() != null) {
//       user.setId(userDTO.id());
//     }

//     user.setName(userDTO.name());
//     user.setEmail(userDTO.email());
//     user.setPassword(userDTO.password());
//     user.setStatus("Not-confirmed");
//     return user;
//   }

// }