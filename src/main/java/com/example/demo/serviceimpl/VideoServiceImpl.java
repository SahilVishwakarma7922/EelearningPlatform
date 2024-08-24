package com.example.demo.serviceimpl;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
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
import com.example.demo.dto.CustomPageResponse;
import com.example.demo.dto.ResourceContentType;
import com.example.demo.dto.VideosDto;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.VideoMapper;
import com.example.demo.model.Videos;
import com.example.demo.repo.VideosRepo;
import com.example.demo.service.FileService;
import com.example.demo.service.VideoService;

@Service
public class VideoServiceImpl implements VideoService {

	@Autowired
	private VideosRepo repo;

	@Autowired
	private VideoMapper mapper;

	@Override
	public VideosDto save(VideosDto dto) {
		Videos entity = mapper.toEntity(dto);
		Videos save = repo.save(entity);
		return mapper.toDto(save);
	}

	@Override
	public List<VideosDto> findAll() {
		List<Videos> videos = repo.findAll();
		List<VideosDto> list = videos.stream().map(v -> mapper.toDto(v)).toList();
		return list;
	}

	@Override
	public Optional<VideosDto> findById(Long id) {
		repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("video not found with id " + id));

		Optional<Videos> video = repo.findById(id);
		Optional<VideosDto> vid = video.map(v -> mapper.toDto(v));
		return vid;

	}

	@Override
	public String updateVideos(long id, VideosDto dto) {

		repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("video not found with id " + id));

		Videos entity = mapper.toEntity(dto);
		entity.setVideoId(id);
		repo.save(entity);
		return "Succesfully saved the video with id " + id;

	}

	@Override
	public String deleteVideos(long id) {

		repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("video not found with id " + id));

		repo.deleteById(id);
		return "Succesfully deleted the video with id " + id;

	}

	@Override
	public CustomPageResponse<VideosDto> findAllByPagination(int pageNumber, int pageSize, String field,
			String direction) {

		Sort sort = null;
		if (direction.equals("asc")) {
			sort = Sort.by(field).ascending();
		} else if (direction.equals("desc")) {
			sort = Sort.by(field).descending();
		}

		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		Page<Videos> video = repo.findAll(pageable);
		List<Videos> videos = video.getContent();
		
		List<VideosDto> vid = videos.stream().map(v -> mapper.toDto(v)).toList();

		CustomPageResponse<VideosDto> custom = new CustomPageResponse<>();
		custom.setContent(vid);
		custom.setPageNumber(pageNumber);
		custom.setPageSize(pageSize);
		custom.setLast(video.isLast());
		custom.setTotalElements(video.getTotalElements());
		custom.setTotalPages(video.getTotalPages());

		return custom;
	}

	
	@Autowired
	private FileService fileService;
	
	@Override
	public VideosDto uploadVideoForVideo(long vidId, MultipartFile file) throws IOException {
	
		repo.findById(vidId).orElseThrow(()-> new ResourceNotFoundException("video not present with id "+vidId));
		Videos videos = repo.findById(vidId).get();
		
		String fullPath = fileService.save(file, AppConstants.VIDEO_UPLOAD_DIR, file.getOriginalFilename());
		videos.setContentType(file.getContentType());
		videos.setFilepath(fullPath);
//		videos.setSize1(file.getSize());
		Videos save = repo.save(videos);
		VideosDto dto = mapper.toDto(save);
		return dto;
	}
	
	
	@Override
	public ResourceContentType getVideoFromVideo(Long id) {
		Videos video = repo.findById(id).orElseThrow(()-> new ResourceNotFoundException("course not found with id"+id));
		String contenttype=video.getContentType();
		String videopath = video.getFilepath();
		Path path=Paths.get(videopath);
		Resource resource=new FileSystemResource(path);
		ResourceContentType resourceContentType=new ResourceContentType();
		resourceContentType.setResource(resource);
		resourceContentType.setContenttype(contenttype);
		return resourceContentType;
		 
	}
}
