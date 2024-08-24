package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.dto.CategoriesDto;
import com.example.demo.dto.CustomPageResponse;
import com.example.demo.dto.UserDto;

public interface CategoriesService {

	public CategoriesDto save(CategoriesDto dto);
	public List<CategoriesDto> findAll();
	public Optional<CategoriesDto> findById(Long id);
	public String updateCategory(Long id,CategoriesDto dto);
	public String deleteCategory(Long id);
	public CustomPageResponse<CategoriesDto> findAllCategoryByPagination(int pageNumber,int pageSize,String field,String direction);
}
