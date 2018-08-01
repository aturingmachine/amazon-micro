package com.example.amazonshipments.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.net.ConnectException;
import java.util.NoSuchElementException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
      HttpHeaders headers, HttpStatus status, WebRequest request) {
    String error = "Malformed JSON request";
    return buildResponseEntity(new ApiException(HttpStatus.BAD_REQUEST, error, ex));
  }

  @ExceptionHandler({NoSuchElementException.class, EmptyResultDataAccessException.class,
      EntityNotFoundException.class})
  protected ResponseEntity<Object> handleNotFoundException(Exception ex) {
    String error = "Resource Not Found";
    return buildResponseEntity(new ApiException(HttpStatus.NOT_FOUND, error, ex));
  }

  @ExceptionHandler(HttpClientErrorException.class)
  protected ResponseEntity<Object> handleCrossOriginMissingException(HttpClientErrorException ex) {
    String error = "Cross Origin Resource Could Not Be Found";

    return buildResponseEntity(new ApiException(HttpStatus.NOT_FOUND, error, ex));
  }

  @ExceptionHandler({ConnectException.class, IllegalStateException.class})
  protected ResponseEntity<Object> handleServiceDownException(Exception ex) {
    String error = "A Remote Service is Unavailable";

    return buildResponseEntity(new ApiException(HttpStatus.FAILED_DEPENDENCY, error, ex));
  }

  private ResponseEntity<Object> buildResponseEntity(ApiException apiException) {
    return new ResponseEntity<>(apiException, apiException.getStatus());
  }

}
