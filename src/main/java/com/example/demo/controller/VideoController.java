package com.example.demo.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.AppConstants;
import com.example.demo.dto.CustomPageResponse;
import com.example.demo.dto.ResourceContentType;
import com.example.demo.dto.VideosDto;
import com.example.demo.exception.CustomMessage;
import com.example.demo.service.VideoService;

@RestController
@RequestMapping("/api/v1/videos")
public class VideoController {

	@Autowired
	private VideoService service;

	@PostMapping
	public ResponseEntity<?> saveVideo(@RequestBody VideosDto dto) {
		
		return new ResponseEntity<>(service.save(dto), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<?> getAll() {
		return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable long id) {
		return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateVideo(@PathVariable long id, @RequestBody VideosDto dto) {
		return new ResponseEntity<>(service.updateVideos(id, dto), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteVideo(@PathVariable long id) {
		return new ResponseEntity<>(service.deleteVideos(id), HttpStatus.OK);
	}

	@GetMapping("/page")
	public ResponseEntity<?> findAllVideoByPagination(
			@RequestParam(value = "pageNumber", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int pageNumber,
			@RequestParam(value = "pageSize", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int pageSize,
			@RequestParam(value = "field",required = false) String field,
			@RequestParam(value = "direction")String direction
			) {
		CustomPageResponse<VideosDto> videos = service.findAllByPagination(pageNumber, pageSize,field,direction);
		return new ResponseEntity<>(videos, HttpStatus.OK);
	}

	@PutMapping("/uploadvideo/{vidId}")
	public ResponseEntity<?> uploadVideoForVideo(@PathVariable long vidId,@RequestParam("video") MultipartFile video) throws IOException{
		
		String contenttype=video.getContentType();
		
		if (contenttype==null) {
			CustomMessage customMessage=new CustomMessage();
			customMessage.setMessage("Please select video file");
			customMessage.setSuccess(false);
			return new ResponseEntity<>(customMessage,HttpStatus.BAD_REQUEST);
		}
		if (!contenttype.equals("video/mp4")) {
			CustomMessage customMessage=new CustomMessage();
			customMessage.setMessage("invalid file type only mp4 can be uploaded");
			customMessage.setSuccess(false);
			return new ResponseEntity<>(customMessage,HttpStatus.BAD_REQUEST);
		}
		
		
		VideosDto dto = service.uploadVideoForVideo(vidId, video);
		return new ResponseEntity<>(dto,HttpStatus.OK);
		
	}
	
	
	@GetMapping("/servvideo/{vidId}")
	public ResponseEntity<?> serverBanners(@PathVariable long vidId){
		ResourceContentType resource = service.getVideoFromVideo(vidId);
		
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(resource.getContenttype()))
				.body(resource.getResource());
		
	}
	
	
	
	
}
