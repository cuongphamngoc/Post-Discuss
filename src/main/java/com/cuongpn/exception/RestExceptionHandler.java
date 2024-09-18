package com.cuongpn.exception;
import com.cuongpn.dto.responeDTO.ResponseError;
import org.springframework.http.HttpStatus;

import org.springframework.validation.FieldError;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class RestExceptionHandler   {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseError handleValidationError(MethodArgumentNotValidException ex,WebRequest request){
        List<String> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage).toList();
        String requestPath = request.getDescription(false).replace("uri=", "");
        return ResponseError.builder()
                .path(requestPath)
                .errors(errors)
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(new Date())
                .build();


    }
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseError handlerDuplicatedUsernameException(IllegalArgumentException e, WebRequest request){
        String requestPath = request.getDescription(false).replace("uri=", "");
        return ResponseError.builder()
                .timestamp(new Date())
                .path(requestPath)
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Username is taken")
                .build();
    }
    @ExceptionHandler(StorageException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseError handleFileException(StorageException e, WebRequest request){
        String requestPath = request.getDescription(false).replace("uri=", "");
        return ResponseError.builder()
                .timestamp(new Date())
                .message(e.getMessage())
                .error("File exception")
                .status(HttpStatus.BAD_REQUEST.value())
                .path(requestPath)
                .build();

    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseError handlerGlobalException(Exception e, WebRequest request){
        String requestPath = request.getDescription(false).replace("uri=", "");
        return ResponseError.builder()
                .timestamp(new Date())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .path(requestPath)
                .error(e.getMessage())
                .build();

    }








}
