package com.ameya.mymovieplan.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.ameya.mymovieplan.entity.Movie;
import com.ameya.mymovieplan.exception.ExceptionConstants;
import com.ameya.mymovieplan.exception.movie.NoSuchMovieException;
import com.ameya.mymovieplan.repository.MovieRepository;
import com.ameya.mymovieplan.service.ImageStorageService;
import com.ameya.mymovieplan.utils.OutputMessage;
import com.ameya.mymovieplan.utils.UserIdGenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageStorageServiceImpl implements ImageStorageService {
	
	@Autowired
	Environment env;
	
	@Autowired
	MovieRepository movieRepository;
	
	@Autowired
	UserIdGenerator uidgen;

	@Override
	public OutputMessage uploadImage(MultipartFile imageFile, int movieId) throws IOException, NoSuchMovieException {
		
		String path = env.getProperty("project.image");
		
		String filename = imageFile.getOriginalFilename();
		
		String newFileName = uidgen.generateUserId(10) + "-" + filename;
		
		String filePath = path + File.separator + newFileName;
		
		File f = new File(path);
		
		if(!f.exists()) {
			f.mkdir();
		}
		
		Files.copy(imageFile.getInputStream(), Paths.get(filePath));
		
		Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new NoSuchMovieException(env.getProperty(ExceptionConstants.MOVIE_NOT_FOUND.toString())));
		
		movie.setPoster(filePath);
		
		movieRepository.save(movie);
		
		OutputMessage outputMessage = new OutputMessage();
		outputMessage.setMessage(filePath);
		
		return outputMessage;
	}

	@Override
	public InputStream downloadImage(String fileName) throws FileNotFoundException {
		
		String path = env.getProperty("project.image");
		
		return new FileInputStream(path + File.separator + fileName);
	}

	@Override
	public void deleteImage(String fileName) {
		
		String path = env.getProperty("project.image");
		
		String fullFilePath = path + File.separator + fileName;
		
		File f = new File(fullFilePath);
		
		f.delete();
		
	}

}
