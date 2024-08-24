package com.example.demo.serviceimpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.demo.dto.CategoriesDto;
import com.example.demo.dto.CustomPageResponse;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.CategoriesMapper;
import com.example.demo.model.Categories;
import com.example.demo.repo.CategoriesRepo;
import com.example.demo.service.CategoriesService;

@Service
public class CategoriesServiceImpl implements CategoriesService {

	@Autowired
	private CategoriesRepo categoriesRepo;

	@Autowired
	private CategoriesMapper mapper;

	@Override
	public CategoriesDto save(CategoriesDto dto) {
		Categories entity = mapper.toEntity(dto);
		Date date = new Date();
		entity.setAddedDate(date);
		Categories save = categoriesRepo.save(entity);
		return mapper.toDto(save);
	}

	@Override
	public List<CategoriesDto> findAll() {
		List<Categories> category = categoriesRepo.findAll();
		List<CategoriesDto> list = category.stream().map(c -> mapper.toDto(c)).toList();
		return list;
	}

	@Override
	public Optional<CategoriesDto> findById(Long id) {
		categoriesRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("no category found with id " + id));
		Optional<Categories> cat = categoriesRepo.findById(id);
		Optional<CategoriesDto> cg = cat.map(c -> mapper.toDto(c));
		return cg;
	}

	@Override
	public String updateCategory(Long id, CategoriesDto dto) {

		categoriesRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("no category found with id " + id));

		Categories cat = mapper.toEntity(dto);
		cat.setId(id);
		categoriesRepo.save(cat);
		return " succesfully updated the category with id " + id;
	}

	@Override
	public String deleteCategory(Long id) {
		categoriesRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("no category found with id " + id));
		categoriesRepo.deleteById(id);
		return " succesfully deleted the category with id " + id;
	}

	@Override
	public CustomPageResponse<CategoriesDto> findAllCategoryByPagination(int pageNumber, int pageSize, String field,
			String direction) {

		Sort sort = null;
		if (direction.equals("asc")) {
			sort = Sort.by(field).ascending();
		} else if (direction.equals("desc")) {
			sort = Sort.by(field).descending();
		}

		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

		Page<Categories> catg = categoriesRepo.findAll(pageable);
		List<Categories> content = catg.getContent();
		List<CategoriesDto> catlist = content.stream().map(ct -> mapper.toDto(ct)).toList();

		CustomPageResponse<CategoriesDto> custom = new CustomPageResponse<>();
		custom.setContent(catlist);
		custom.setPageNumber(pageNumber);
		custom.setPageSize(pageSize);
		custom.setLast(catg.isLast());
		custom.setTotalElements(catg.getTotalElements());
		custom.setTotalPages(catg.getTotalPages());

		return custom;
	}

}
