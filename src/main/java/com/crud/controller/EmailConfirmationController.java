package com.crud.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crud.util.EnableUserService;

@RestController
@RequestMapping
public class EmailConfirmationController {
  private final EnableUserService enableUserService;

  public EmailConfirmationController(EnableUserService enableUserService) {
    this.enableUserService = enableUserService;
  }

  @GetMapping("/verify")
  public void enableEmail(@RequestParam(value="token") String token) {
    enableUserService.enableUser(token);

  }
}
