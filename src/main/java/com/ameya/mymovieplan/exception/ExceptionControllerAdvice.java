package com.ameya.mymovieplan.exception;

import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import com.ameya.mymovieplan.exception.city.CityAlreadyExistsException;
import com.ameya.mymovieplan.exception.city.NoSuchCityException;
import com.ameya.mymovieplan.exception.genre.GenreAlreadyExistsException;
import com.ameya.mymovieplan.exception.genre.NoSuchGenreException;
import com.ameya.mymovieplan.exception.language.LanguageAlreadyExistsException;
import com.ameya.mymovieplan.exception.language.NoSuchLanguageException;
import com.ameya.mymovieplan.exception.showtime.NoSuchShowtimeException;
import com.ameya.mymovieplan.exception.showtime.ShowtimeAlreadyExistsException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionControllerAdvice {
	
	@Autowired
	private Environment environment;
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorMessage> exceptionHandlerCustom(Exception ex){
		ErrorMessage error = new ErrorMessage();
		error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		error.setMessage(environment.getProperty(ExceptionConstants.GENERAL_EXCEPTION.toString()));
		return new ResponseEntity<>(error, HttpStatus.OK);
		
	}
	
	private ErrorMessage generateException(Exception ex) {
		ErrorMessage error = new ErrorMessage();
		error.setErrorCode(HttpStatus.BAD_REQUEST.value());
		error.setMessage(ex.getMessage());
		return error;
	}
	
	@ExceptionHandler(NoSuchUserException.class)
	public ResponseEntity<ErrorMessage> exceptionHandlerCustom(NoSuchUserException ex){
		ErrorMessage error = new ErrorMessage();
		error.setErrorCode(HttpStatus.BAD_REQUEST.value());
		error.setMessage(ex.getMessage());
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler(UserAlreadyExistsException.class)
	public ResponseEntity<ErrorMessage> exceptionHandlerCustom(UserAlreadyExistsException ex){
		ErrorMessage error = generateException(ex);
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler(CityAlreadyExistsException.class)
	public ResponseEntity<ErrorMessage> exceptionHandlerCustom(CityAlreadyExistsException ex){
		ErrorMessage error = generateException(ex);
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler(NoSuchCityException.class)
	public ResponseEntity<ErrorMessage> exceptionHandlerCustom(NoSuchCityException ex){
		ErrorMessage error = generateException(ex);
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler(NoSuchGenreException.class)
	public ResponseEntity<ErrorMessage> exceptionHandlerCustom(NoSuchGenreException ex){
		ErrorMessage error = generateException(ex);
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler(GenreAlreadyExistsException.class)
	public ResponseEntity<ErrorMessage> exceptionHandlerCustom(GenreAlreadyExistsException ex){
		ErrorMessage error = generateException(ex);
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler(NoSuchLanguageException.class)
	public ResponseEntity<ErrorMessage> exceptionHandlerCustom(NoSuchLanguageException ex){
		ErrorMessage error = generateException(ex);
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler(LanguageAlreadyExistsException.class)
	public ResponseEntity<ErrorMessage> exceptionHandlerCustom(LanguageAlreadyExistsException ex){
		ErrorMessage error = generateException(ex);
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler(NoSuchShowtimeException.class)
	public ResponseEntity<ErrorMessage> exceptionHandlerCustom(NoSuchShowtimeException ex){
		ErrorMessage error = generateException(ex);
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler(ShowtimeAlreadyExistsException.class)
	public ResponseEntity<ErrorMessage> exceptionHandlerCustom(ShowtimeAlreadyExistsException ex){
		ErrorMessage error = generateException(ex);
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorMessage> handleValidationExceptions(MethodArgumentNotValidException ex) 
	{
		 ErrorMessage error = new ErrorMessage();
	     error.setErrorCode(HttpStatus.BAD_REQUEST.value());
	     error.setMessage(ex.getBindingResult().getAllErrors()
	    		 		  	.stream().map(ObjectError::getDefaultMessage)
	    		 		  	.collect(Collectors.joining(", ")));
	        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ErrorMessage> handleConstraintValidationExceptions(ConstraintViolationException ex) 
	{
		 ErrorMessage error = new ErrorMessage();
	     error.setErrorCode(HttpStatus.BAD_REQUEST.value());
	     error.setMessage(ex.getConstraintViolations()
	    		 			.stream().map(ConstraintViolation::getMessage)
	    		 			.collect(Collectors.joining(", ")));
	     return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

}
