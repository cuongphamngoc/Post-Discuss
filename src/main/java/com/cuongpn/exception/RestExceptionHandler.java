package com.cuongpn.exception;
import com.cuongpn.dto.responeDTO.ResponseError;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import java.util.Date;
import java.util.Map;

@RestControllerAdvice
public class RestExceptionHandler   {
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseError handlerDuplicatedUsernameException(IllegalArgumentException e, WebRequest request){
        ResponseError responseError = new ResponseError();
        responseError.setTimestamp(new Date());
        responseError.setStatus(HttpStatus.BAD_REQUEST.value());
        responseError.setMessage(e.getMessage());
        responseError.setError("Bad Request");
        String requestPath = request.getDescription(false).replace("uri=", "");
        responseError.setPath(requestPath);
        return responseError;
    }
    @ExceptionHandler(StorageException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseError handleFileException(StorageException e, WebRequest request){
        ResponseError responseError = new ResponseError();
        responseError.setTimestamp(new Date());
        responseError.setStatus(HttpStatus.BAD_REQUEST.value());
        responseError.setMessage(e.getMessage());
        responseError.setError("File Exception");
        String requestPath = request.getDescription(false).replace("uri=", "");
        responseError.setPath(requestPath);
        return responseError;
    }

    @ExceptionHandler(Exception.class)
    public ResponseError handlerGlobalException(Exception e, WebRequest request){
        ResponseError responseError = new ResponseError();
        responseError.setTimestamp(new Date());
        responseError.setStatus(HttpStatus.BAD_REQUEST.value());
        responseError.setMessage(e.getMessage());
        responseError.setError("Bad Request");
        String requestPath = request.getDescription(false).replace("uri=", "");
        responseError.setPath(requestPath);
        return responseError;
    }








}
