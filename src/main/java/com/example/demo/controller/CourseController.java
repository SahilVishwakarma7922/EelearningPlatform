package com.example.demo.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.AppConstants;
import com.example.demo.dto.CategoriesDto;
import com.example.demo.dto.CoursesDto;
import com.example.demo.dto.CustomPageResponse;
import com.example.demo.dto.ResourceContentType;
import com.example.demo.model.Courses;
import com.example.demo.service.CourseService;
import com.example.demo.serviceimpl.FileServiceImpl;

@RestController
@RequestMapping("/api/v1/course")
public class CourseController {

	@Autowired
	private CourseService courseService;

	@PostMapping
	public ResponseEntity<?> save(@RequestBody CoursesDto dto) {
		return new ResponseEntity<>(courseService.saveCourse(dto), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<?> getall() {
		return new ResponseEntity<>(courseService.getAll(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable long id) {
		return new ResponseEntity<>(courseService.findById(id), HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateCourse(@PathVariable long id, @RequestBody CoursesDto coursesDto) {
		return new ResponseEntity<>(courseService.updateCourse(id, coursesDto), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteCourse(@PathVariable long id) {
		return new ResponseEntity<>(courseService.deleteCourse(id), HttpStatus.OK);
	}

	@GetMapping("/page")
	public ResponseEntity<?> findAllCategoryByPagination(
			@RequestParam(value = "pageNumber", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int pageNumber,
			@RequestParam(value = "pageSize", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int pageSize,
			@RequestParam(value = "field")String field,
			@RequestParam(value = "direction")String direction
			
			) {
		CustomPageResponse<CoursesDto> allCourseByPagination = courseService.findAllCourseByPagination(pageNumber,
				pageSize,field,direction);
		return new ResponseEntity<>(allCourseByPagination, HttpStatus.OK);
	}
	

	@PostMapping("/uploadbannertocourse/{courseId}")
	public ResponseEntity<?> uploadImage(@PathVariable long courseId,@RequestParam("file") MultipartFile file) throws IOException{
		
		System.out.println(file.getContentType());
		System.out.println(file.getOriginalFilename());
		System.out.println(file.getContentType());
		CoursesDto course = courseService.saveBanner(file, courseId); 
		
		
		return new ResponseEntity<>(course,HttpStatus.OK);
	}
	
	@GetMapping("/{courseId}/banners")
	public ResponseEntity<?> serverBanners(@PathVariable long courseId){
		ResourceContentType resource = courseService.getCourseBannerById(courseId);
		
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(resource.getContenttype()))
				.body(resource.getResource());
		
	}
	
	
	@Autowired
	private FileServiceImpl fileService;
	
	@PostMapping("/img/{courseId}")
	public ResponseEntity<?> saveCourseWithImg(@PathVariable long courseId,@RequestParam MultipartFile file) throws IOException {
		
		return new ResponseEntity<>(fileService.save(file, "output", file.getOriginalFilename()),HttpStatus.OK);
		
	}
	
	

}
