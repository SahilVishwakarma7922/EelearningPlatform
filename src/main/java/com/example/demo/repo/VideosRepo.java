package com.example.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Videos;

public interface VideosRepo extends JpaRepository<Videos, Long>{

}
