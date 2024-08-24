package com.example.demo.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

	public String save(MultipartFile file,String outputpath,String filename)  throws IOException;
}
