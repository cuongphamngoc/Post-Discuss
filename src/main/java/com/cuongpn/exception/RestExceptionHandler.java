package com.cuongpn.exception;
import com.cuongpn.dto.responeDTO.ResponseError;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import java.util.Date;
@RestControllerAdvice
public class RestExceptionHandler   {
    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseError handlerDuplicatedUsernameException(UserAlreadyExistException e, WebRequest request){
        ResponseError responseError = new ResponseError();
        responseError.setTimestamp(new Date());
        responseError.setStatus(HttpStatus.BAD_REQUEST.value());
        responseError.setMessage(e.getMessage());
        responseError.setError("Bad Request");
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
