package com.example.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Categories;

public interface CategoriesRepo extends JpaRepository<Categories, Long>{

}
