package com.example.elctronic.store.ElectronicStore.exception;

import com.example.elctronic.store.ElectronicStore.dtos.ApiResponseMessage;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.logging.Logger;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private Logger logger = (Logger) LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponseMessage> resourceNotFoundExceptionHandler(ResourceNotFoundException ex)
    {
        logger.info("Exception Handler invoked !!");
        ApiResponseMessage response = ApiResponseMessage.builder().message(ex.getMessage()).status(HttpStatus.NOT_FOUND).success(true).build();

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
