package br.com.lucas.todo_list.errors;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandleController {

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<String> HttpMessageNotReadableException(HttpMessageNotReadableException e) {
    return ResponseEntity.status(400).body(e.getMostSpecificCause().getMessage());
  }
}
