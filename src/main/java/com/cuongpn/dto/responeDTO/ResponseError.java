package com.cuongpn.dto.responeDTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter

public class ResponseError {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date timestamp;
    private int status;
    private String path;
    private String error;
    private String message;



    public ResponseError( int status, String error, String message, String path) {
        this.timestamp = new Date();
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }

}
