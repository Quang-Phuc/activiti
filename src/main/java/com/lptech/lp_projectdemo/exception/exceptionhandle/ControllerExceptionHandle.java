package com.lptech.lp_projectdemo.exception.exceptionhandle;

import com.lptech.lp_projectdemo.exception.customexception.RequestNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

@RestControllerAdvice
public class ControllerExceptionHandle {
    @ExceptionHandler(RequestNotFoundException.class)
    ResponseEntity<String> handleRequestNotFoundException(RequestNotFoundException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RuntimeException.class)
    ResponseEntity<String> handleRuntimeException(RequestNotFoundException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({IOException.class})
    ResponseEntity<String> handleIOException(IOException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }


}
