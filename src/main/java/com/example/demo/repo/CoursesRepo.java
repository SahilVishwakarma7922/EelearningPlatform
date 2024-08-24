package com.example.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Courses;

public interface CoursesRepo extends JpaRepository<Courses, Long>{

}
