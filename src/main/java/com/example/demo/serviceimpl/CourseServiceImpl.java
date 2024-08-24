package com.example.demo.serviceimpl;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.AppConstants;
import com.example.demo.dto.CoursesDto;
import com.example.demo.dto.CustomPageResponse;
import com.example.demo.dto.ResourceContentType;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.CoursesMapper;
import com.example.demo.model.Courses;
import com.example.demo.repo.CoursesRepo;
import com.example.demo.service.CourseService;
import com.example.demo.service.FileService;

@Service
public class CourseServiceImpl implements CourseService {

	@Autowired
	private CoursesRepo coursesRepo;
	@Autowired
	private CoursesMapper mapper;
	@Autowired
	private FileService fileService;
	
	
	@Override
	public CoursesDto saveCourse(CoursesDto coursesDto) {
		Courses entity = mapper.toEntity(coursesDto);
		Date date = new Date();
		entity.setCreatedDate(date);
		Courses courses = coursesRepo.save(entity);
		return mapper.toDto(courses);
	}

	@Override
	public List<CoursesDto> getAll() {
		List<Courses> courses = coursesRepo.findAll();
		List<CoursesDto> course = courses.stream().map(cs -> mapper.toDto(cs)).toList();
		return course;
	}

	@Override
	public Optional<CoursesDto> findById(long id) {
		coursesRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("no Course found with id " + id));
		Optional<Courses> course = coursesRepo.findById(id);
		Optional<CoursesDto> courses = course.map(cs -> mapper.toDto(cs));
		return courses;
	}

	@Override
	public String updateCourse(Long id, CoursesDto dto) {
		coursesRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("no Course found with id " + id));
		Courses entity = mapper.toEntity(dto);
		entity.setId(id);
		coursesRepo.save(entity);
		return "succesfully updated the course with id " + id;
	}

	@Override
	public String deleteCourse(Long id) {
		coursesRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("no Course found with id " + id));
		coursesRepo.deleteById(id);
		return " Succesfully deleted the course with id " + id;

	}

	@Override
	public CustomPageResponse<CoursesDto> findAllCourseByPagination(int pageNumber, int pageSize, String field,
			String direction) {

		Sort sort = null;

		if (direction.equals("asc")) {
			sort = Sort.by(field).ascending();
		} else if (direction.equals("desc")) {
			sort = Sort.by(field).descending();
		}

		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		Page<Courses> courses = coursesRepo.findAll(pageable);
		List<CoursesDto> course = courses.stream().map(cs -> mapper.toDto(cs)).toList();
		CustomPageResponse<CoursesDto> custom = new CustomPageResponse<>();
		custom.setContent(course);
		custom.setLast(courses.isLast());
		custom.setPageNumber(pageNumber);
		custom.setPageSize(pageSize);
		custom.setTotalElements(courses.getTotalElements());
		custom.setTotalPages(courses.getTotalPages());

		return custom;
	}

	@Override
	public CoursesDto saveBanner(MultipartFile file, Long courseId) throws IOException {
		
		Courses course = coursesRepo.findById(courseId).orElseThrow(()-> new ResourceNotFoundException("no course found with id "+courseId));
		String path = fileService.save(file, AppConstants.COURSE_BANNER_UPLOAD_DIR, file.getOriginalFilename());
		course.setContenttype(file.getContentType());
		course.setBannerName(path);
		Courses save = coursesRepo.save(course);
		CoursesDto dto = mapper.toDto(save);
		return dto;
	}

	@Override
	public ResourceContentType getCourseBannerById(Long id) {
		Courses course = coursesRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("course not found with id"+id));
		String contenttype=course.getContenttype();
		String bannerName = course.getBannerName();
		Path path=Paths.get(bannerName);
		Resource resource=new FileSystemResource(path);
		ResourceContentType resourceContentType=new ResourceContentType();
		 resourceContentType.setResource(resource);
		 resourceContentType.setContenttype(contenttype);
		 return resourceContentType;
		 
	}
	
	

	
	

}





