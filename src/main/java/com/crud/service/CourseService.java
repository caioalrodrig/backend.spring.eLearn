package com.crud.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import com.crud.dto.CourseDTO;
import com.crud.dto.mapper.CourseMapper;
import com.crud.repository.CourseRepository;
import com.exception.RecordNotFoundException;

@Validated
@Service
public class CourseService {
  
  private final CourseRepository courseRepository;
  private final CourseMapper courseMapper;

  public CourseService(CourseRepository courseRepository, CourseMapper courseMapper){
    this.courseRepository = courseRepository;
    this.courseMapper = courseMapper;
  }

  public List<CourseDTO> read(){
    
    return courseRepository.findAll()
     .stream()
     .map(course -> courseMapper.toDTO(course))
     .collect(Collectors.toList());
  }

  public CourseDTO findById(@NotNull @Positive Long id){
    
    return courseRepository.findById(id)
     .map(course -> courseMapper.toDTO(course))
     .orElseThrow(() -> new RecordNotFoundException(id));
  }

  public CourseDTO create(@Valid CourseDTO courseDTO){
    
    return courseMapper.toDTO(courseRepository.save(courseMapper.toEntity(courseDTO)));
  }

  public void deleteById(@NotNull @Positive Long id){
    
    courseRepository.delete(courseRepository.findById(id)
      .orElseThrow(() -> new RecordNotFoundException(id)));  
  }

  public CourseDTO updateById(@NotNull @Positive Long id, CourseDTO courseDTO){

    return courseMapper.toDTO(courseRepository.findById(id)
      .map(record -> {
        record.setName(courseDTO.name());
        record.setCategory(courseDTO.category());
        return courseRepository.save(record);
      })
      .orElseThrow(() -> new RecordNotFoundException(id)));
  }
}
