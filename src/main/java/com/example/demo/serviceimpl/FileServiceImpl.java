package com.example.demo.serviceimpl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.service.FileService;

@Service
public class FileServiceImpl implements FileService{

	@Override
	public String save(MultipartFile file, String outputpath, String filename) throws IOException {
		
		Path path=Paths.get(outputpath);
		
		Files.createDirectories(path);
		
		
		Path path2=Paths.get(path.toString(),file.getOriginalFilename());
		
		Files.copy(file.getInputStream(),path2, StandardCopyOption.REPLACE_EXISTING);
		
		
		return path2.toString();
	}

	
	
	
	
	
}
