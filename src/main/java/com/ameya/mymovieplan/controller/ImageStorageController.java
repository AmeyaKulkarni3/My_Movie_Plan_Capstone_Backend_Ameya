package com.ameya.mymovieplan.controller;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import com.ameya.mymovieplan.exception.movie.NoSuchMovieException;
import com.ameya.mymovieplan.service.ImageStorageService;
import com.ameya.mymovieplan.utils.OutputMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
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
	ImageStorageService imageStorageService;

	@PostMapping("/add-image/{movieId}")
	public ResponseEntity<OutputMessage> uploadImage(@RequestParam("file") MultipartFile file, @PathVariable int movieId) throws IOException, NoSuchMovieException{
		
		return ResponseEntity.ok(imageStorageService.uploadImage(file, movieId));
	}
	
	@GetMapping(value = "/download/{filename}", produces = {MediaType.IMAGE_JPEG_VALUE})
	public void downloadFile(@PathVariable String filename, HttpServletResponse response) throws IOException{
		
		InputStream image = imageStorageService.downloadImage(filename);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		
		
		StreamUtils.copy(image, response.getOutputStream());
	}
}
