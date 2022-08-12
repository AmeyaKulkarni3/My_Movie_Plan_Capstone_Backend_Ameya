package com.ameya.mymovieplan.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import com.ameya.mymovieplan.firebase.FirebaseStorageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/images")
public class ImageStorageController {
	
	@Autowired
	FirebaseStorageService firebaseStorageService;

	@PostMapping("/add-image")
	public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) throws IOException{
		firebaseStorageService.uploadFile(file);
		return ResponseEntity.ok("Success");
	}
	
	@GetMapping("/download/{filename:.+}")
	public ResponseEntity<Object> downloadFile(@PathVariable String filename, HttpServletRequest request) throws IOException{
		return firebaseStorageService.dowloadFile(filename, request);
	}
}
