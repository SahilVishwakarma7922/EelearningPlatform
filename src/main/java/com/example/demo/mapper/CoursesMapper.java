package com.example.demo.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.dto.CoursesDto;
import com.example.demo.model.Courses;

@Component
public class CoursesMapper {

@Autowired
private Mapper mapper;

public Courses toEntity(CoursesDto coursesDto) {
	return mapper.modelMapper().map(coursesDto, Courses.class);
}

public CoursesDto toDto(Courses courses) {
	return mapper.modelMapper().map(courses, CoursesDto.class);
}



}
