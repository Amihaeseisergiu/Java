package com.amihaeseisergiu.serverapplication;

import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {
    
    @ExceptionHandler(value = CustomException.class)
    public ResponseEntity<ErrorResponse> 
        handlePlayerNotFoundException(CustomException e)
        {
            ErrorResponse error = new ErrorResponse(e.getMessage());
            error.setTimestamp(LocalDateTime.now());
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
}
