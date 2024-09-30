package com.crud.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

import com.crud.dto.CourseDTO;
import com.crud.service.CourseService;

import jakarta.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/course")
public class CourseController {

  private final CourseService courseService;

  public CourseController(CourseService courseService) {
    this.courseService = courseService;
  }

  @GetMapping
  public List<CourseDTO> readCourse() {

    return courseService.read();
  }

  @GetMapping("/{id}")
  public CourseDTO readCourseById(@PathVariable Long id) {

    return courseService.findById(id);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public CourseDTO createCourse(@RequestBody CourseDTO courseDTO) {
  
    return courseService.create(courseDTO);
  }

  @PutMapping("/{id}")
  public CourseDTO updateCourse(@PathVariable Long id, @RequestBody CourseDTO courseDTO) {

    return courseService.updateById(id, courseDTO);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteCourseById(@PathVariable @NotNull Long id) {
    
    courseService.deleteById(id);
  }

  
}
