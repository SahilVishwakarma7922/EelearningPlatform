package com.example.demo.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.CoursesDto;
import com.example.demo.dto.CustomPageResponse;
import com.example.demo.dto.ResourceContentType;
import com.example.demo.model.Courses;

public interface CourseService {

	public CoursesDto saveCourse(CoursesDto coursesDto);
	public List<CoursesDto> getAll();
	public Optional<CoursesDto> findById(long id);
	public String updateCourse(Long id,CoursesDto dto);
	public String deleteCourse(Long id);
	public CustomPageResponse<CoursesDto> findAllCourseByPagination(int pageNumber,int pageSize,String field,String direction);
	
	
	public CoursesDto saveBanner(MultipartFile file,Long courseId) throws IOException ;
	public ResourceContentType getCourseBannerById(Long id);
}
