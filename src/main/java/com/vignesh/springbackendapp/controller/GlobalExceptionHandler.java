package com.vignesh.springbackendapp.controller;

import com.vignesh.springbackendapp.service.DuplicateUserEntryException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(DuplicateUserEntryException.class)
  public ResponseEntity handleDuplicateUserEntryException(DuplicateUserEntryException ex) {
    return ResponseEntity.badRequest().body(ex.getMessage());
  }
}
