package com.example.demo.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.dto.CategoriesDto;
import com.example.demo.model.Categories;

@Component
public class CategoriesMapper {

	@Autowired
	public Mapper mapper;
	
	public Categories toEntity(CategoriesDto categoriesDto) {
		return mapper.modelMapper().map(categoriesDto, Categories.class);
	}
	
	public CategoriesDto toDto(Categories categories) {
		return mapper.modelMapper().map(categories, CategoriesDto.class);
	}
	
}
