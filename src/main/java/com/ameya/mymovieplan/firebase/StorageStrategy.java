package com.ameya.mymovieplan.firebase;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface StorageStrategy {
	
	String[] uploadFile(MultipartFile multipartFile) throws IOException;
	
	ResponseEntity<Object> dowloadFile(String filename, HttpServletRequest request) throws IOException;

}
