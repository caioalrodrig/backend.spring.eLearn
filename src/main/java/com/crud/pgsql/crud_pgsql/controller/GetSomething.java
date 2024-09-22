package com.crud.pgsql.crud_pgsql.controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
 
@RestController
@RequestMapping("/api")
public class GetSomething {
  
  @GetMapping("/something") 
  public ResponseEntity<String> getJew() {
    System.out.print("hi");
    return ResponseEntity.ok().body("{\"helloMsg\": \"Hi\"}");
  }

  
}
