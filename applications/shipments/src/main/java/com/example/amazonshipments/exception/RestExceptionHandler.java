package com.example.amazonshipments.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
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

  @ExceptionHandler({DataIntegrityViolationException.class})
  protected ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
    String error = "Violation Of Unique Constraint: ";
    String mes = ex.getMostSpecificCause().getMessage();
    error += mes.substring(0, mes.indexOf("'", mes.indexOf("'") + 2));
    return buildResponseEntity(new ApiException(HttpStatus.CONFLICT, error, ex));
  }

  private ResponseEntity<Object> buildResponseEntity(ApiException apiException) {
    return new ResponseEntity<>(apiException, apiException.getStatus());
  }

}
