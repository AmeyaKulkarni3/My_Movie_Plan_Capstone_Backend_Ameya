package com.ameya.mymovieplan.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import com.ameya.mymovieplan.exception.movie.NoSuchMovieException;
import com.ameya.mymovieplan.utils.OutputMessage;

import org.springframework.web.multipart.MultipartFile;

public interface ImageStorageService {
	
	OutputMessage uploadImage(MultipartFile imageFile, int movieId) throws IOException, NoSuchMovieException;
	
	InputStream downloadImage(String fullFileName) throws FileNotFoundException;
	
	void deleteImage(String fileName);

}
