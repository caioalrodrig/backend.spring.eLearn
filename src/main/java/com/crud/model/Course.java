package com.crud.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Pattern;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;


@Data
@Entity
public class Course {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(length = 20, nullable = false)
  private String category;

  @Column(length = 10, nullable=false)
  @Pattern(regexp = "Ativo|Inativo")
  private String status= "Ativo";

}