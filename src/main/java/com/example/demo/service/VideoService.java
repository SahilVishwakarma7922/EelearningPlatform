package com.example.demo.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.CustomPageResponse;
import com.example.demo.dto.ResourceContentType;
import com.example.demo.dto.VideosDto;

public interface VideoService {

	public VideosDto save(VideosDto dto);
	public List<VideosDto> findAll();
	public Optional<VideosDto> findById(Long id);
	public String updateVideos(@PathVariable long id,VideosDto dto);
	public String deleteVideos(@PathVariable long id);
	
	public CustomPageResponse<VideosDto> findAllByPagination(int pageNumber,int pageSize,String field,String direction);
	
	public VideosDto uploadVideoForVideo(long vidId,MultipartFile file) throws IOException ;
	public ResourceContentType getVideoFromVideo(Long id);
}
