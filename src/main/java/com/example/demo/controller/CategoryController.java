package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.example.demo.dto.AppConstants;
import com.example.demo.dto.CategoriesDto;
import com.example.demo.dto.CustomPageResponse;
import com.example.demo.service.CategoriesService;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
	
	@Autowired
	private CategoriesService categoriesService;
	
	@PostMapping
	public ResponseEntity<?> save(@RequestBody CategoriesDto dto){
		return new ResponseEntity<>(categoriesService.save(dto),HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable long id){
		return new ResponseEntity<>(categoriesService.findById(id),HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<?> getAllCategory(){
		return new ResponseEntity<>(categoriesService.findAll(),HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateCategory(@PathVariable long id,@RequestBody CategoriesDto dto){
		return new ResponseEntity<>(categoriesService.updateCategory(id, dto),HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteCategory(@PathVariable long id){
		return new ResponseEntity<>(categoriesService.deleteCategory(id),HttpStatus.OK);
	}

	
	@GetMapping("/page")
	public ResponseEntity<?> findAllCategoryByPagination
	(
	@RequestParam(value = "pageNumber",required = false,defaultValue = AppConstants.DEFAULT_PAGE_NUMBER)int pageNumber,
	@RequestParam(value = "pageSize",required = false,defaultValue = AppConstants.DEFAULT_PAGE_SIZE)int pageSize,
	@RequestParam(value = "field",required = false) String field,
	@RequestParam(value = "direction")String direction
	)
	{
		CustomPageResponse<CategoriesDto> allCategoryByPagination = categoriesService.findAllCategoryByPagination(pageNumber, pageSize,field,direction);
		return new ResponseEntity<>(allCategoryByPagination,HttpStatus.OK);
	}
	
	
	
	
}
