package com.example.demo.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.dto.VideosDto;
import com.example.demo.model.Videos;

@Component
public class VideoMapper {

	@Autowired
	private Mapper mapper;
	
	public Videos toEntity(VideosDto dto) {
		return mapper.modelMapper().map(dto, Videos.class);
	}
	
	public VideosDto toDto(Videos videos) {
		return mapper.modelMapper().map(videos, VideosDto.class);
	}
	
}
